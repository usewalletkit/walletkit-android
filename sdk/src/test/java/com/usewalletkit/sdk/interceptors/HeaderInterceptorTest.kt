package com.usewalletkit.sdk.interceptors

import junit.framework.TestCase.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

class HeaderInterceptorTest {

    @Test
    fun `test headers added`() {
        val mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().setBody("ack"))

        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(HeadersInterceptor("myproject"))
            .build()

        client.sendRequest(mockWebServer)

        val headers = mockWebServer.takeRequest().headers

        assertEquals("application/json", headers["Content-Type"])
        assertEquals("myproject", headers["X-WalletKit-Project-ID"])
    }
}
