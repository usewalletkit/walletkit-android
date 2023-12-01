package com.usewalletkit.sdk

import com.google.firebase.auth.FirebaseAuth
import com.usewalletkit.sdk.auth.AuthProvider
import com.usewalletkit.sdk.generated.infrastructure.ApiClient
import com.usewalletkit.sdk.interceptors.AuthInterceptor
import com.usewalletkit.sdk.interceptors.HeadersInterceptor
import com.usewalletkit.sdk.login.WalletKitLoginClient
import io.github.jan.supabase.gotrue.GoTrue
import okhttp3.OkHttpClient

class WalletKitClient private constructor(
    private val authProvider: AuthProvider,
    projectId: String,
    baseUrl: String = DEFAULT_BASE_URL,
) {
    constructor(
        loginClient: WalletKitLoginClient,
    ) : this(
        authProvider = AuthProvider.WalletKitAuthProvider(client = loginClient),
        projectId = loginClient.projectId,
        baseUrl = loginClient.baseUrl,
    )
    constructor(
        loginClient: GoTrue,
        projectId: String,
        baseUrl: String = DEFAULT_BASE_URL,
    ) : this(
        authProvider = AuthProvider.SupabaseAuthProvider(client = loginClient),
        projectId = projectId,
        baseUrl = baseUrl,
    )
    constructor(
        firebaseAuth: FirebaseAuth,
        projectId: String,
        baseUrl: String = DEFAULT_BASE_URL,
    ) : this(
        authProvider = AuthProvider.FirebaseAuthProvider(auth = firebaseAuth),
        projectId = projectId,
        baseUrl = baseUrl,
    )

    private val apiClient = ApiClient(
        baseUrl = baseUrl,
        okHttpClientBuilder = OkHttpClient()
            .newBuilder()
            .addInterceptor(HeadersInterceptor(projectId = projectId))
            .addInterceptor(AuthInterceptor(authProvider = authProvider))
    )

    suspend fun isLoggedIn() = authProvider.isLoggedIn()

    suspend fun getOwnerId() = authProvider.getOwnerId()

    fun <S> getService(serviceClass: Class<S>): S =
        apiClient.createService(serviceClass = serviceClass)

    companion object {
        private const val DEFAULT_BASE_URL = "https://testnet.usewalletkit.com"
    }
}
