package com.usewalletkit.sdk.auth

import com.google.firebase.auth.FirebaseAuth
import com.usewalletkit.sdk.generated.models.TokenSource
import com.usewalletkit.sdk.login.WalletKitLoginClient
import io.github.jan.supabase.gotrue.GoTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock

sealed interface AuthProvider {
    fun shouldRefreshToken(): Boolean
    fun getSource(): TokenSource
    suspend fun refreshToken()
    suspend fun getAuthToken(): String?
    suspend fun isLoggedIn(): Boolean
    suspend fun getOwnerId(): String?
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

        override suspend fun getOwnerId() = client.getOwnerId()

        override suspend fun logout() = client.logout()
    }

    class SupabaseAuthProvider(
        private val client: GoTrue,
    ) : AuthProvider {

        init {
            runBlocking {
                client.loadFromStorage()
            }
        }

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

        override suspend fun getOwnerId() = client.currentSessionOrNull()?.user?.id

        override suspend fun logout() = client.logout()

        private fun currentSession() = client.currentSessionOrNull()
    }

    class FirebaseAuthProvider(
        private val auth: FirebaseAuth,
    ) : AuthProvider {
        override fun shouldRefreshToken() = runBlocking {
            val token = auth.getAccessToken(false).await()
            token.expirationTimestamp > System.currentTimeMillis()
        }

        override fun getSource() = TokenSource.FIREBASE

        override suspend fun refreshToken() {
            auth.getAccessToken(true).await().token
        }

        override suspend fun getAuthToken() = auth.getAccessToken(false).await().token

        override suspend fun isLoggedIn() = auth.currentUser != null

        override suspend fun getOwnerId() = auth.currentUser?.uid

        override suspend fun logout() = auth.signOut()
    }
}
