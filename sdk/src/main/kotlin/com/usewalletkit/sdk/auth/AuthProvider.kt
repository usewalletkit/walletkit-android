package com.usewalletkit.sdk.auth

import com.usewalletkit.sdk.login.WalletKitLoginClient

sealed interface AuthProvider {
    fun shouldRefreshToken(): Boolean
    suspend fun refreshToken()
    suspend fun getAuthToken(): String?
    suspend fun isLoggedIn(): Boolean
    suspend fun logout()

    class WalletKitAuthProvider(
        private val client: WalletKitLoginClient,
    ) : AuthProvider {
        override fun shouldRefreshToken() = client.shouldRefreshToken()

        override suspend fun refreshToken() {
            client.refreshToken()
        }

        override suspend fun getAuthToken() = client.getAuthToken()

        override suspend fun isLoggedIn() = client.isLoggedIn()

        override suspend fun logout() = client.logout()
    }
}
