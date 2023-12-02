package com.usewalletkit.sdk

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.usewalletkit.sdk.auth.AuthProvider
import com.usewalletkit.sdk.generated.apis.WalletsApi
import com.usewalletkit.sdk.generated.models.TokenSource
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.GoTrueConfig
import io.github.jan.supabase.gotrue.user.AppMetadata
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class WalletKitClientTest {

    @Test
    fun `supabase logged out`() {
        val loginClient: GoTrue = mock {
            on { config } doReturn GoTrueConfig()
            onBlocking { loadFromStorage() } doReturn true
        }
        val client = WalletKitClient(loginClient = loginClient, projectId = "123")

        runBlocking {
            assertFalse(client.isLoggedIn())
            assertNull(client.getOwnerId())
        }
    }

    @Test
    fun `supabase logged in`() {
        val userSession = UserSession(
            accessToken = "ey123",
            refreshToken = "ey456",
            tokenType = "jwt",
            expiresIn = 1000000,
            user = UserInfo(
                aud = "789",
                id = "user_id",
                appMetadata = AppMetadata("", listOf())
            ),
        )
        val loginClient: GoTrue = mock {
            on { config } doReturn GoTrueConfig()
            on { currentSessionOrNull() } doReturn userSession
            onBlocking { loadFromStorage() } doReturn true
        }
        val client = WalletKitClient(loginClient = loginClient, projectId = "123")

        runBlocking {
            assertTrue(client.isLoggedIn())
            assertEquals("user_id", client.getOwnerId())
        }
    }

    @Test
    fun `firebase logged out`() {
        val loginClient: FirebaseAuth = mock()
        val client = WalletKitClient(firebaseAuth = loginClient, projectId = "abc")

        runBlocking {
            assertFalse(client.isLoggedIn())
            assertNull(client.getOwnerId())
        }
    }

    @Test
    fun `firebase logged in`() {
        val firebaseUser: FirebaseUser = mock {
            on { uid } doReturn "123"
        }
        val loginClient: FirebaseAuth = mock {
            on { currentUser } doReturn firebaseUser
        }
        val client = WalletKitClient(
            firebaseAuth = loginClient,
            projectId = "abc",
        )

        runBlocking {
            assertTrue(client.isLoggedIn())
            assertEquals("123", client.getOwnerId())
        }

    }

    @Test
    fun `api request`() {
        val mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().setBody("[]"))
        val authProvider: AuthProvider = mock {
            on { shouldRefreshToken() } doReturn false
            on { getSource() } doReturn TokenSource.WALLETKIT
            onBlocking { getAuthToken() } doReturn "ey123"
            onBlocking { isLoggedIn() } doReturn true
        }
        val client = createWalletKitClient(
            authProvider = authProvider,
            projectId = "123",
            baseUrl = mockWebServer.url("/wallets").toString(),
        )

        val response = runBlocking {
            client.getService(WalletsApi::class.java)
                .walletsList()
        }
        val headers = mockWebServer.takeRequest().headers
        assertNotNull(response.body())
        assertEquals(0, response.body()!!.size)
        assertEquals("application/json", headers["Content-Type"])
        assertEquals("123", headers["X-WalletKit-Project-ID"])
        assertEquals("Bearer ey123", headers["Authorization"])
    }

    private fun createWalletKitClient(
        authProvider: AuthProvider,
        projectId: String,
        baseUrl: String,
    ) = WalletKitClient::class.java.getDeclaredConstructor(
        AuthProvider::class.java,
        String::class.java,
        String::class.java,
    ).let { constructor ->
        constructor.isAccessible = true
        constructor.newInstance(authProvider, projectId, baseUrl)
    }
}
