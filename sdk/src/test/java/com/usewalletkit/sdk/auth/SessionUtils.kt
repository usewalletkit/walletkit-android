package com.usewalletkit.sdk.auth

import com.usewalletkit.sdk.generated.models.Session
import java.time.OffsetDateTime

fun createSessionModel(
    createdAt: OffsetDateTime = OffsetDateTime.now(),
    accessTokenExpiresAt: OffsetDateTime = OffsetDateTime.now(),
    refreshTokenExpiresAt: OffsetDateTime = OffsetDateTime.now(),
) = SessionModel(
    id = "123",
    createdAt = createdAt,
    projectId = "1234",
    userId = "abcd",
    accessToken = "eyJh...",
    accessTokenExpiresAt = accessTokenExpiresAt,
    refreshToken = "eyCn...",
    refreshTokenExpiresAt = refreshTokenExpiresAt,
)

fun createSessionResponse(
    createdAt: OffsetDateTime = OffsetDateTime.now(),
    accessTokenExpiresAt: OffsetDateTime = OffsetDateTime.now(),
    refreshTokenExpiresAt: OffsetDateTime = OffsetDateTime.now(),
) = Session(
    id = "123",
    createdAt = createdAt,
    projectId = "1234",
    userId = "abcd",
    accessToken = "eyJh...",
    accessTokenExpiresAt = accessTokenExpiresAt,
    refreshToken = "eyCn...",
    refreshTokenExpiresAt = refreshTokenExpiresAt,
)

fun getExpirations(createdAt: OffsetDateTime) =
    createdAt.plusMinutes(5) to createdAt.plusDays(30)
