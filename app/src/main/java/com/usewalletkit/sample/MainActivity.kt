package com.usewalletkit.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.usewalletkit.sample.ui.screens.FirebaseScreen
import com.usewalletkit.sample.ui.screens.ProviderSelectionScreen
import com.usewalletkit.sample.ui.screens.SupabaseScreen
import com.usewalletkit.sample.ui.screens.WalletKitScreen
import com.usewalletkit.sample.ui.theme.WalletKitTheme
import com.usewalletkit.sample.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    setContent {
                        MainContent(viewModel, uiState)
                    }
                }
            }
        }
    }

    @Composable
    private fun MainContent(
        viewModel: MainViewModel,
        uiState: MainViewModel.MainUiState,
    ) {
        WalletKitTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                when (uiState.provider) {
                    MainViewModel.Provider.UNSPECIFIED -> ProviderSelectionScreen(
                        lastProjectId = uiState.projectId,
                        lastSupabaseApiKey = uiState.supabaseApiKey,
                        lastSupabaseProjectUrl = uiState.supabaseProjectUrl,
                        onWalletKitSelected = viewModel::onWalletKitSelected,
                        onFirebaseSelected = viewModel::onFirebaseSelected,
                        onSupabaseSelected = viewModel::onSupabaseSelected,
                    )
                    MainViewModel.Provider.WALLETKIT -> WalletKitScreen(
                        projectId = uiState.projectId!!,
                        onCancel = viewModel::onClear,
                    )
                    MainViewModel.Provider.FIREBASE -> FirebaseScreen(
                        projectId = uiState.projectId!!,
                        onCancel = viewModel::onClear,
                    )
                    MainViewModel.Provider.SUPABASE -> SupabaseScreen(
                        projectId = uiState.projectId!!,
                        apiKey = uiState.supabaseApiKey!!,
                        projectUrl = uiState.supabaseProjectUrl!!,
                        onCancel = viewModel::onClear,
                    )
                }
            }
        }
    }
}
