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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.usewalletkit.sample.ui.viewmodels.FirebaseViewModel
import com.usewalletkit.sdk.generated.models.Wallet

@Composable
fun FirebaseScreen(
    projectId: String,
    onCancel: () -> Unit,
) {
    val viewModel: FirebaseViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                FirebaseViewModel(projectId = projectId)
            }
        }
    )

    val currentState = viewModel.uiState.collectAsState()

    if (currentState.value.isLoggedIn) {
        FirebaseMainContent(
            isLoading = currentState.value.isLoading,
            wallets = currentState.value.wallets,
            onFetchWallets = viewModel::fetchWallets,
            onCreateWallets = viewModel::createWallet,
            onLogout = viewModel::onLogout,
        )
    } else {
        FirebaseLogin(
            onLogin = viewModel::onLogin,
            onExit = onCancel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FirebaseLogin(
    onLogin: (String, String) -> Unit,
    onExit: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 64.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Login with Firebase",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )
        Button(
            onClick = { onLogin(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = password.isNotEmpty() && email.isNotEmpty()
        ) {
            Text("Login")
        }
        OutlinedButton(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}

@Composable
private fun FirebaseMainContent(
    isLoading: Boolean,
    wallets: List<Wallet>?,
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
