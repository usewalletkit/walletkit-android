package com.usewalletkit.sample.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WalletKitViewModel(projectId: String) : ViewModel() {

    private val _uiState = MutableStateFlow(
        WalletKitUiState(
            projectId = projectId,
            isLoggedIn = false,
        )
    )

    val uiState: StateFlow<WalletKitUiState> = _uiState.asStateFlow()

    fun onLogin(email: String) {
        _uiState.update { it.copy(isLoggedIn = true) }
    }

    fun onLoginAnonymously() {
        _uiState.update { it.copy(isLoggedIn = true) }
    }

    fun onLogout() {
        _uiState.update { it.copy(isLoggedIn = false) }
    }

    data class WalletKitUiState(
        val projectId: String,
        val isLoggedIn: Boolean,
    )
}
