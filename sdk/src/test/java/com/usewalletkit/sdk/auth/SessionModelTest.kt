package com.usewalletkit.sdk.auth

import com.usewalletkit.sdk.generated.models.Session
import com.usewalletkit.sdk.login.SessionModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.time.OffsetDateTime

class SessionModelTest {
    @Test
    fun `created correctly from session`() {
        val createdAt = OffsetDateTime.now().minusMinutes(10)
        val (accessTokenExpiresAt, refreshTokenExpiresAt) = getExpirations(createdAt)
        val sessionModel = SessionModel.fromSession(
            Session(
                id = "123",
                createdAt = createdAt,
                projectId = "1234",
                userId = "abcd",
                accessToken = "eyJh...",
                accessTokenExpiresAt = accessTokenExpiresAt,
                refreshToken = "eyCn...",
                refreshTokenExpiresAt = refreshTokenExpiresAt,
            )
        )

        assertEquals("123", sessionModel.id)
        assertEquals(createdAt, sessionModel.createdAt)
        assertEquals("1234", sessionModel.projectId)
        assertEquals("abcd", sessionModel.userId)
        assertEquals("eyJh...", sessionModel.accessToken)
        assertEquals(accessTokenExpiresAt, sessionModel.accessTokenExpiresAt)
        assertEquals("eyCn...", sessionModel.refreshToken)
        assertEquals(refreshTokenExpiresAt, sessionModel.refreshTokenExpiresAt)
    }

    @Test
    fun `valid session`() {
        val createdAt = OffsetDateTime.now().minusMinutes(4)
        val (accessTokenExpiresAt, refreshTokenExpiresAt) = getExpirations(createdAt)
        val session = createSessionModel(
            createdAt = createdAt,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
        )

        assertTrue(session.hasValidSession())
    }

    @Test
    fun `expired session`() {
        val createdAt = OffsetDateTime.now().minusDays(31)
        val (accessTokenExpiresAt, refreshTokenExpiresAt) = getExpirations(createdAt)
        val session = createSessionModel(
            createdAt = createdAt,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
        )

        assertFalse(session.hasValidSession())
        assertFalse(session.canRefreshToken())
    }

    @Test
    fun `expired session but still refreshable`() {
        val createdAt = OffsetDateTime.now().minusDays(29)
        val (accessTokenExpiresAt, refreshTokenExpiresAt) = getExpirations(createdAt)
        val session = createSessionModel(
            createdAt = createdAt,
            accessTokenExpiresAt = accessTokenExpiresAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
        )

        assertFalse(session.hasValidSession())
        assertTrue(session.canRefreshToken())
    }
}