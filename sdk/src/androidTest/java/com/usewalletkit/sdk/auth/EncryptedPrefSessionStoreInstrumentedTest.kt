package com.usewalletkit.sdk.auth

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.usewalletkit.sdk.login.EncryptedPrefSessionStore
import com.usewalletkit.sdk.login.SessionModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.time.OffsetDateTime

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EncryptedPrefSessionStoreInstrumentedTest {

    @Test
    fun testStoreAndGet() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val store = EncryptedPrefSessionStore(appContext)

        val createdAt = OffsetDateTime.now().minusMinutes(10)
        val accessTokenExpiresAt = createdAt.plusMinutes(5)
        val refreshTokenExpiresAt = createdAt.plusDays(30)
        val session = SessionModel(
            id = "123",
            createdAt = createdAt,
            projectId = "1234",
            userId = "abcd",
            accessToken = "eyJh...",
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshToken = "eyCn...",
            refreshTokenExpiresAt = refreshTokenExpiresAt,
        )

        store.storeSession(session)
        val sessionModel = store.getSession()

        assertNotNull(sessionModel)
        assertEquals("123", sessionModel!!.id)
        assertEquals(createdAt, sessionModel.createdAt)
        assertEquals("1234", sessionModel.projectId)
        assertEquals("abcd", sessionModel.userId)
        assertEquals("eyJh...", sessionModel.accessToken)
        assertEquals(accessTokenExpiresAt, sessionModel.accessTokenExpiresAt)
        assertEquals("eyCn...", sessionModel.refreshToken)
        assertEquals(refreshTokenExpiresAt, sessionModel.refreshTokenExpiresAt)
    }
}