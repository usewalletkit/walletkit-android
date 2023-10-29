package com.usewalletkit.sample.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.usewalletkit.sdk.WalletKitClient
import com.usewalletkit.sdk.generated.apis.WalletsApi
import com.usewalletkit.sdk.generated.models.CreateWalletRequest
import com.usewalletkit.sdk.generated.models.ListWalletsResponseItem
import com.usewalletkit.sdk.generated.models.Network
import com.usewalletkit.sdk.generated.models.WalletControlMode
import com.usewalletkit.sdk.login.WalletKitLoginClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WalletKitViewModel(
    projectId: String,
    context: Context,
) : ViewModel() {

    private val loginClient = WalletKitLoginClient(
        projectId = projectId,
        context = context,
    )

    private val walletKitClient = WalletKitClient(
        loginClient = loginClient,
    )

    private val _uiState = MutableStateFlow(
        WalletKitUiState(
            isLoggedIn = loginClient.isLoggedIn(),
        )
    )

    val uiState: StateFlow<WalletKitUiState> = _uiState.asStateFlow()

    fun onLogin(email: String) {
        _uiState.update { it.copy(isLoading = true) }
        GlobalScope.async {
            val response = loginClient.login(email = email)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    shouldVerifyCode = response.isSuccessful,
                    userId = response.body()!!.userId
                )
            }
        }
    }

    fun onVerifyCode(code: String) {
        _uiState.update { it.copy(isLoading = true) }
        GlobalScope.async {
            val response = loginClient.verifyCode(
                userId = uiState.value.userId!!,
                verificationCode = code,
            )
            _uiState.update {
                it.copy(
                    isLoading = false,
                    shouldVerifyCode = false,
                    isLoggedIn = response.isSuccessful,
                )
            }
        }
    }

    fun onCancelVerification() {
        _uiState.update { it.copy(isLoading = false, shouldVerifyCode = false) }
    }

    fun onLoginAnonymously() {
        _uiState.update { it.copy(isLoading = true) }

        GlobalScope.async {
            val response = loginClient.loginAnonymously()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = response.isSuccessful,
                )
            }
        }
    }

    fun onLogout() {
        _uiState.update { it.copy(isLoggedIn = false) }
        loginClient.logout()
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
                        ownerId = "426ce30e-cdef-40e3-8981-773de834e4a3"
                    )
                )
            _uiState.update { it.copy(isLoading = false) }
            if (response.isSuccessful) {
                fetchWallets()
            }
        }

    }

    data class WalletKitUiState(
        val isLoggedIn: Boolean,
        val isLoading: Boolean = false,
        val shouldVerifyCode: Boolean = false,
        val userId: String? = null,
        val wallets: List<ListWalletsResponseItem>? = null,
    )
}
