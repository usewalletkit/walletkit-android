package com.usewalletkit.sample.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.usewalletkit.sdk.WalletKitClient
import com.usewalletkit.sdk.generated.apis.WalletsApi
import com.usewalletkit.sdk.generated.models.CreateWalletRequest
import com.usewalletkit.sdk.generated.models.Network
import com.usewalletkit.sdk.generated.models.Wallet
import com.usewalletkit.sdk.generated.models.WalletControlMode
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SupabaseViewModel(
    projectId: String,
    apiKey: String,
    projectUrl: String,
) : ViewModel() {

    private val loginClient = createSupabaseClient(
        supabaseUrl = projectUrl,
        supabaseKey = apiKey
    ) {
        install(GoTrue)
    }.gotrue

    private val walletKitClient = WalletKitClient(
        loginClient = loginClient,
        projectId = projectId,
    )

    private val _uiState = MutableStateFlow(
        SupabaseUiState(
            isLoggedIn = loginClient.isLoggedIn(),
        )
    )

    val uiState: StateFlow<SupabaseUiState> = _uiState.asStateFlow()

    fun onLogin(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }
        GlobalScope.async {
            loginClient.loginWith(Email) {
                this.email = email
                this.password = password
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = loginClient.isLoggedIn(),
                )
            }
        }
    }

    fun onLogout() {
        _uiState.update { it.copy(isLoggedIn = false) }
        GlobalScope.async {
            loginClient.logout()
        }
    }

    fun fetchWallets() {
        _uiState.update { it.copy(isLoading = true) }
        GlobalScope.async {
            val response = walletKitClient.getService(WalletsApi::class.java)
                .walletsList()
            if (response.isSuccessful && response.body() != null) {
                _uiState.update {
                    it.copy(isLoading = false, wallets = response.body())
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun createWallet() {
        _uiState.update { it.copy(isLoading = true) }
        GlobalScope.async {
            val response = walletKitClient.getService(WalletsApi::class.java)
                .walletsCreate(
                    CreateWalletRequest(
                        name = "My first wallet",
                        network = Network.ETHEREUM,
                        controlMode = WalletControlMode.USER,
                        developerSecret = "testnet-secret",
                        userPin = "111111",
                        ownerId = walletKitClient.getOwnerId(),
                    )
                )
            _uiState.update { it.copy(isLoading = false) }
            if (response.isSuccessful) {
                fetchWallets()
            }
        }
    }

    private fun GoTrue.isLoggedIn() = currentSessionOrNull() != null

    data class SupabaseUiState(
        val isLoggedIn: Boolean,
        val isLoading: Boolean = false,
        val wallets: List<Wallet>? = null,
    )
}
