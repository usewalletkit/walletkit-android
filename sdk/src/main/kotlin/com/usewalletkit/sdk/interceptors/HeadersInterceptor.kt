package com.usewalletkit.sdk.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(
    private val projectId: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("X-WalletKit-Project-ID", projectId)
            .build()

        return chain.proceed(request)
    }
}
