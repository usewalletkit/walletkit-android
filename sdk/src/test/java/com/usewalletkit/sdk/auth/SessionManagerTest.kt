package com.usewalletkit.sdk.auth

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime

class SessionManagerTest {

    private lateinit var sessionManager: SessionManager

    @Before
    fun setup() {
        sessionManager = SessionManager(TestSessionStore())
    }

    @Test
    fun `no session`() {
        assertFalse(sessionManager.hasValidSession())
    }

    @Test
    fun `valid session`() {
        val createdAt = OffsetDateTime.now()
        val (accessTokenExpiresAt, refreshTokenExpiresAt) = getExpirations(createdAt)
        val session = createSessionResponse(
            createdAt = createdAt,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
        )
        sessionManager.storeSession(session)

        assertTrue(sessionManager.hasValidSession())
        assertTrue(sessionManager.canRefreshToken())
    }

    @Test
    fun `expired session`() {
        val createdAt = OffsetDateTime.now().minusDays(31)
        val (accessTokenExpiresAt, refreshTokenExpiresAt) = getExpirations(createdAt)
        val session = createSessionResponse(
            createdAt = createdAt,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
        )
        sessionManager.storeSession(session)

        assertFalse(sessionManager.hasValidSession())
        assertFalse(sessionManager.canRefreshToken())
    }

    @Test
    fun `expired session but still refreshable`() {
        val createdAt = OffsetDateTime.now().minusDays(29)
        val (accessTokenExpiresAt, refreshTokenExpiresAt) = getExpirations(createdAt)
        val session = createSessionResponse(
            createdAt = createdAt,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
        )
        sessionManager.storeSession(session)

        assertFalse(sessionManager.hasValidSession())
        assertTrue(sessionManager.canRefreshToken())
    }

    @Test
    fun `get token`() {
        val session = createSessionResponse()
        sessionManager.storeSession(session)

        assertEquals("eyJh...", sessionManager.getAuthToken())
    }

    private class TestSessionStore : SessionStore {

        private var session: SessionModel? = null

        override fun storeSession(session: SessionModel) {
            this.session = session
        }

        override fun getSession(): SessionModel? = session
    }
}
