package com.usewalletkit.sample.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SupabaseViewModel(projectId: String, apiKey: String) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SupabaseUiState(
            projectId = projectId,
            apiKey = apiKey,
            isLoggedIn = false,
        )
    )

    val uiState: StateFlow<SupabaseUiState> = _uiState.asStateFlow()

    fun onLogin(email: String, password: String) {
        _uiState.update { it.copy(isLoggedIn = true) }
    }

    fun onLogout() {
        _uiState.update { it.copy(isLoggedIn = false) }
    }

    data class SupabaseUiState(
        val projectId: String,
        val apiKey: String,
        val isLoggedIn: Boolean,
    )
}
