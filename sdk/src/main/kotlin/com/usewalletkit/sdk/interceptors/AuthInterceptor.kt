package com.usewalletkit.sdk.interceptors

import com.usewalletkit.sdk.auth.AuthProvider
import com.usewalletkit.sdk.generated.models.TokenSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class AuthInterceptor(private val authProvider: AuthProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            // refresh token if expired before the call
            if (authProvider.shouldRefreshToken()) {
                authProvider.refreshToken()
            }
            // add authorization header and proceed
            val response = chain.proceedWithAuth(
                bearerToken = checkNotNull(authProvider.getAuthToken()),
                tokenSource = authProvider.getSource(),
            )

            // if unauthorized cause token expired in the meanwhile refresh token
            if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                response.close()
                authProvider.refreshToken()
                // and retry
                chain.proceedWithAuth(
                    bearerToken = checkNotNull(authProvider.getAuthToken()),
                    tokenSource = authProvider.getSource(),
                )
            } else {
                response
            }
        }
    }

    private fun Interceptor.Chain.proceedWithAuth(
        bearerToken: String,
        tokenSource: TokenSource,
    ) = proceed(
        request = request().newBuilder()
            .addHeader("Authorization", "Bearer $bearerToken")
            .addHeader("X-WalletKit-Token-Source", "${tokenSource.value}")
            .build()
    )
}
