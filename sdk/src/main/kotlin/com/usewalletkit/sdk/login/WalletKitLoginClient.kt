package com.usewalletkit.sdk.login

import android.content.Context
import com.usewalletkit.sdk.generated.apis.UsersApi
import com.usewalletkit.sdk.generated.infrastructure.ApiClient
import com.usewalletkit.sdk.generated.models.LoginWithEmailResponse
import com.usewalletkit.sdk.generated.models.Session
import com.usewalletkit.sdk.generated.models.UsersLoginWithEmailRequest
import com.usewalletkit.sdk.generated.models.UsersRefreshTokenRequest
import com.usewalletkit.sdk.generated.models.UsersVerifyLoginRequest
import com.usewalletkit.sdk.interceptors.HeadersInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response

class WalletKitLoginClient(
    context: Context,
    val projectId: String,
    val baseUrl: String = DEFAULT_BASE_URL,
) {
    private val apiClient = ApiClient(
        baseUrl = baseUrl,
        okHttpClientBuilder = OkHttpClient()
            .newBuilder()
            .addInterceptor(HeadersInterceptor(projectId = projectId))
    )

    private val sessionManager = SessionManager(
        sessionStore = EncryptedPrefSessionStore(context),
        usersApi = apiClient.createService(UsersApi::class.java)
    )

    suspend fun login(email: String) : Response<LoginWithEmailResponse> =
        apiClient.createService(UsersApi::class.java)
            .usersLoginWithEmail(
                UsersLoginWithEmailRequest(
                    email = email,
                ),
            )

    suspend fun verifyCode(userId: String, verificationCode: String) : Response<Session> {
        val response = apiClient.createService(UsersApi::class.java)
            .usersVerifyLogin(
                UsersVerifyLoginRequest(
                    userId = userId,
                    verificationCode = verificationCode,
                ),
            )
        maybeStoreSession(response)
        return response
    }

    suspend fun loginAnonymously() : Response<Session> {
        val response = apiClient.createService(UsersApi::class.java)
            .usersLoginAnonymously()
        maybeStoreSession(response)
        return response
    }

    suspend fun refreshToken() : Response<Session> {
        val response = apiClient.createService(UsersApi::class.java)
            .usersRefreshToken(
                usersRefreshTokenRequest = UsersRefreshTokenRequest(
                    sessionId = sessionManager.getSessionId(),
                    refreshToken = sessionManager.getRefreshToken(),
                )
            )
        maybeStoreSession(response)
        return response
    }

    fun isLoggedOut(): Boolean = !isLoggedIn()

    private fun maybeStoreSession(response: Response<Session>) {
        if (response.isSuccessful) {
            sessionManager.storeSession(response.body()!!)
        }
    }

    fun getAuthToken(): String? = sessionManager.getAuthToken()

    fun getOwnerId(): String? = sessionManager.getOwnerId()
    fun isLoggedIn(): Boolean = sessionManager.hasValidSession()
    fun logout() = sessionManager.logout()

    fun shouldRefreshToken() = sessionManager.shouldRefreshToken()

    companion object {
        private const val DEFAULT_BASE_URL = "https://testnet.usewalletkit.com"
    }
}
