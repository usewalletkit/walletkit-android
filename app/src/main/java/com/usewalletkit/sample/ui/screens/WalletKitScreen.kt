package com.usewalletkit.sample.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.usewalletkit.sample.ui.viewmodels.WalletKitViewModel
import com.usewalletkit.sdk.generated.models.ListWalletsResponseItem

@Composable
fun WalletKitScreen(
    projectId: String,
    onCancel: () -> Unit,
) {
    val viewModel: WalletKitViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                WalletKitViewModel(
                    projectId = projectId,
                    context = application.applicationContext,
                )
            }
        }
    )

    val currentState = viewModel.uiState.collectAsState()

    if (currentState.value.isLoggedIn) {
        WalletKitMainContent(
            isLoading = currentState.value.isLoading,
            wallets = currentState.value.wallets,
            onLogout = viewModel::onLogout,
            onCreateWallets = viewModel::createWallet,
            onFetchWallets = viewModel::fetchWallets,
        )
    } else if (currentState.value.shouldVerifyCode) {
        WalletKitVerifyCode(
            isLoading = currentState.value.isLoading,
            onVerifyCode = viewModel::onVerifyCode,
            onCancel = viewModel::onCancelVerification,
        )
    } else {
        WalletKitLogin(
            isLoginIn = currentState.value.isLoading,
            onLogin = viewModel::onLogin,
            onLoginAnonymously = viewModel::onLoginAnonymously,
            onExit = onCancel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalletKitLogin(
    isLoginIn: Boolean,
    onLogin: (String) -> Unit,
    onLoginAnonymously: () -> Unit,
    onExit: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 64.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Login with WalletKit",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = { onLogin(email) },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotEmpty() && !isLoginIn,
        ) {
            Text("Login")
        }
        Button(
            onClick = onLoginAnonymously,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoginIn,
        ) {
            Text("Login Anonymously")
        }
        OutlinedButton(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalletKitVerifyCode(
    isLoading: Boolean,
    onVerifyCode: (String) -> Unit,
    onCancel: () -> Unit,
) {
    var code by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 64.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Enter email code",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Email Code") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = { onVerifyCode(code) },
            modifier = Modifier.fillMaxWidth(),
            enabled = code.isNotEmpty() && !isLoading,
        ) {
            Text("Verify")
        }
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}


@Composable
private fun WalletKitMainContent(
    isLoading: Boolean,
    wallets: List<ListWalletsResponseItem>?,
    onFetchWallets: () -> Unit,
    onCreateWallets: () -> Unit,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 64.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onFetchWallets,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch wallets")
        }
        Button(
            onClick = onCreateWallets,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create wallets")
        }
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
        if (isLoading) {
            Text("Loading wallets")
        } else {
            if (wallets != null) {
                if (wallets.isEmpty()) {
                    Text("No wallets created yet")
                }
                for (wallet in wallets) {
                    Text("Name: ${wallet.name}")
                    Text("Network: ${wallet.network}")
                    Text("Address: ${wallet.address}")
                }
            }
        }
    }
}
