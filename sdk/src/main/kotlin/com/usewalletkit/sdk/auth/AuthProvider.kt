package com.usewalletkit.sdk.auth

import com.usewalletkit.sdk.generated.models.TokenSource
import com.usewalletkit.sdk.login.WalletKitLoginClient
import io.github.jan.supabase.gotrue.GoTrue
import kotlinx.datetime.Clock

sealed interface AuthProvider {
    fun shouldRefreshToken(): Boolean
    fun getSource(): TokenSource
    suspend fun refreshToken()
    suspend fun getAuthToken(): String?
    suspend fun isLoggedIn(): Boolean
    suspend fun logout()

    class WalletKitAuthProvider(
        private val client: WalletKitLoginClient,
    ) : AuthProvider {
        override fun shouldRefreshToken() = client.shouldRefreshToken()

        override fun getSource() = TokenSource.WALLETKIT

        override suspend fun refreshToken() {
            client.refreshToken()
        }

        override suspend fun getAuthToken() = client.getAuthToken()

        override suspend fun isLoggedIn() = client.isLoggedIn()

        override suspend fun logout() = client.logout()
    }

    class SupabaseAuthProvider(
        private val client: GoTrue,
    ) : AuthProvider {
        override fun shouldRefreshToken(): Boolean {
            val currentSession = currentSession()
            val currentTimestamp = Clock.System.now().toEpochMilliseconds()
            return currentSession != null &&
                    currentSession.expiresAt.toEpochMilliseconds() < currentTimestamp
        }

        override fun getSource() = TokenSource.SUPABASE

        override suspend fun refreshToken() = client.refreshCurrentSession()

        override suspend fun getAuthToken() = client.currentSessionOrNull()!!.accessToken

        override suspend fun isLoggedIn() = client.currentSessionOrNull() != null

        override suspend fun logout() = client.logout()

        private fun currentSession() = client.currentSessionOrNull()
    }
}
