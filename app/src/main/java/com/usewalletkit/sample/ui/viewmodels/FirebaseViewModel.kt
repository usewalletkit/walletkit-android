package com.usewalletkit.sample.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.usewalletkit.sdk.WalletKitClient
import com.usewalletkit.sdk.generated.apis.WalletsApi
import com.usewalletkit.sdk.generated.models.CreateWalletRequest
import com.usewalletkit.sdk.generated.models.ListWalletsResponseItem
import com.usewalletkit.sdk.generated.models.Network
import com.usewalletkit.sdk.generated.models.WalletControlMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FirebaseViewModel(projectId: String) : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private val walletKitClient = WalletKitClient(
        firebaseAuth = auth,
        projectId = projectId,
    )

    private val _uiState = MutableStateFlow(
        FirebaseUiState(
            isLoggedIn = auth.currentUser != null,
        )
    )

    val uiState: StateFlow<FirebaseUiState> = _uiState.asStateFlow()

    fun onLogin(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(Dispatchers.IO.asExecutor()) { task ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = task.isSuccessful,
                    )
                }
            }
    }

    fun onLogout() {
        auth.signOut()
        _uiState.update { it.copy(isLoggedIn = false) }
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

    data class FirebaseUiState(
        val isLoggedIn: Boolean,
        val isLoading: Boolean = false,
        val wallets: List<ListWalletsResponseItem>? = null,
    )
}
