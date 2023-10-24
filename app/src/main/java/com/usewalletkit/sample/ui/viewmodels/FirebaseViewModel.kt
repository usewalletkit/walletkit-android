package com.usewalletkit.sample.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FirebaseViewModel(projectId: String) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FirebaseUiState(
            projectId = projectId,
            isLoggedIn = false,
        )
    )

    val uiState: StateFlow<FirebaseUiState> = _uiState.asStateFlow()

    fun onLogin(email: String, password: String) {
        _uiState.update { it.copy(isLoggedIn = true) }
    }

    fun onLogout() {
        _uiState.update { it.copy(isLoggedIn = false) }
    }

    data class FirebaseUiState(
        val projectId: String,
        val isLoggedIn: Boolean,
    )
}
