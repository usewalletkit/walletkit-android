package com.usewalletkit.sdk.interceptors

import com.usewalletkit.sdk.auth.AuthProvider
import com.usewalletkit.sdk.generated.models.TokenSource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verifyBlocking

class AuthInterceptorTest {

    @Test
    fun `do not refresh token`() {
        val mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().setBody("ack"))

        val authProvider: AuthProvider = mock {
            on { shouldRefreshToken() } doReturn false
            on { getSource() } doReturn TokenSource.WALLETKIT
            onBlocking { getAuthToken() } doReturn "ey123"
        }
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor(authProvider))
            .build()
        val response = client.sendRequest(mockWebServer)
        val headers = mockWebServer.takeRequest().headers

        verifyBlocking(authProvider, never()) { refreshToken() }
        assertEquals("Bearer ey123", headers["Authorization"])
        assertEquals("walletkit", headers["X-WalletKit-Token-Source"])
        assertTrue(response.isSuccessful)
        assertEquals("ack", response.body?.string())

        response.close()
    }

    @Test
    fun `refresh token`() {
        val mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().setBody("ack"))

        val authProvider: AuthProvider = mock {
            on { shouldRefreshToken() } doReturn true
            on { getSource() } doReturn TokenSource.WALLETKIT
            onBlocking { getAuthToken() } doReturn "ey123"
        }
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor(authProvider))
            .build()
        val response = client.sendRequest(mockWebServer)
        val headers = mockWebServer.takeRequest().headers

        verifyBlocking(authProvider) { refreshToken() }
        assertEquals("Bearer ey123", headers["Authorization"])
        assertEquals("walletkit", headers["X-WalletKit-Token-Source"])
        assertTrue(response.isSuccessful)
        assertEquals("ack", response.body?.string())

        response.close()
    }

    @Test
    fun `test unauthorized`() {
        val mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().setResponseCode(401))
        mockWebServer.enqueue(MockResponse().setBody("ack"))

        val authProvider: AuthProvider = mock {
            on { shouldRefreshToken() } doReturn false
            on { getSource() } doReturn TokenSource.WALLETKIT
            onBlocking { getAuthToken() } doReturn "ey123"
        }
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor(authProvider))
            .build()
        val response = client.sendRequest(mockWebServer)
        val headers = mockWebServer.takeRequest().headers

        // verify it tries to refresh the token after receiving a 401
        verifyBlocking(authProvider) { refreshToken() }
        assertEquals("Bearer ey123", headers["Authorization"])
        assertEquals("walletkit", headers["X-WalletKit-Token-Source"])
        assertTrue(response.isSuccessful)
        assertEquals("ack", response.body?.string())

        response.close()
    }
}
