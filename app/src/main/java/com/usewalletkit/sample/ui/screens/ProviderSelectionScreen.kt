package com.usewalletkit.sample.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderSelectionScreen(
    lastProjectId: String?,
    lastSupabaseApiKey: String?,
    onWalletKitSelected: (String) -> Unit,
    onFirebaseSelected: (String) -> Unit,
    onSupabaseSelected: (String, String) -> Unit,
) {
    var projectId by rememberSaveable { mutableStateOf(lastProjectId ?: "") }
    var supabaseApiKey by rememberSaveable { mutableStateOf(lastSupabaseApiKey ?: "") }
    val buttonsEnabled = projectId.isNotEmpty()

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Select login provider",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            value = projectId,
            onValueChange = { projectId = it },
            label = { Text("WalletKit Project Id") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                onWalletKitSelected(projectId)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonsEnabled,
        ) {
            Text("Login with WalletKit")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onFirebaseSelected(projectId)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonsEnabled,
        ) {
            Text("Login with Firebase")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            value = supabaseApiKey,
            onValueChange = { supabaseApiKey = it },
            label = { Text("Supabase Api Key") },
            singleLine = true
        )
        Button(
            onClick = {
                onSupabaseSelected(projectId, supabaseApiKey)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = buttonsEnabled && supabaseApiKey.isNotEmpty(),
        ) {
            Text("Login with Supabase")
        }
    }
}
