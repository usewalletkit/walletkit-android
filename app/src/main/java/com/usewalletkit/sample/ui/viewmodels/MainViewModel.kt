package com.usewalletkit.sample.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val sharedPrefs: SharedPreferences) : ViewModel() {

    private val _uiState = MutableStateFlow(loadState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onWalletKitSelected(projectId: String) {
        saveState(
            provider = Provider.WALLETKIT,
            projectId = projectId,
        )
    }

    fun onFirebaseSelected(projectId: String) {
        saveState(
            provider = Provider.FIREBASE,
            projectId = projectId,
        )
    }

    fun onSupabaseSelected(projectId: String, apiKey: String) {
        saveState(
            provider = Provider.SUPABASE,
            projectId = projectId,
            supabaseApiKey = apiKey,
        )
    }

    fun onClear() {
        saveState(provider = Provider.UNSPECIFIED)
    }

    private fun saveState(
        provider: Provider,
        projectId: String? = uiState.value.projectId,
        supabaseApiKey: String? = uiState.value.supabaseApiKey,
    ) {
        _uiState.update { MainUiState(provider, projectId, supabaseApiKey) }
        val stateJson = Gson().toJson(uiState.value)
        sharedPrefs
            .edit()
            .putString(MAIN_UI_STATE_KEY, stateJson)
            .commit()
    }

    private fun loadState(): MainUiState {
        val stateJson = sharedPrefs.getString(MAIN_UI_STATE_KEY, null)

        return if (stateJson != null) {
            Gson().fromJson(stateJson, MainUiState::class.java)
        } else {
            MainUiState()
        }
    }

    data class MainUiState(
        val provider: Provider = Provider.UNSPECIFIED,
        val projectId: String? = null,
        val supabaseApiKey: String? = null,
    )

    enum class Provider {
        UNSPECIFIED,
        WALLETKIT,
        FIREBASE,
        SUPABASE,
    }

    companion object {
        private const val MAIN_PREFS_NAME = "main_prefs_name"
        private const val MAIN_UI_STATE_KEY = "main_ui_state_key"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val sharedPrefs =
                    application.getSharedPreferences(MAIN_PREFS_NAME, Context.MODE_PRIVATE)
                MainViewModel(
                    sharedPrefs = sharedPrefs,
                )
            }
        }
    }
}
