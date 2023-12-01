package com.usewalletkit.sdk.interceptors

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer


fun OkHttpClient.sendRequest(mockWebServer: MockWebServer) = newCall(
    Request.Builder().url(mockWebServer.url("/")).build()
).execute()
