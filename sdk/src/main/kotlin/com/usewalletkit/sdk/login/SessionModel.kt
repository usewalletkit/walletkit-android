package com.usewalletkit.sdk.login

import android.os.Parcelable
import com.usewalletkit.sdk.generated.models.Session
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@Parcelize
data class SessionModel(
    val id: String,
    val createdAt: OffsetDateTime,
    val projectId: String,
    val userId: String,
    val accessToken: String,
    val accessTokenExpiresAt: OffsetDateTime,
    val refreshToken: String,
    val refreshTokenExpiresAt: OffsetDateTime

) : Parcelable {

    fun hasValidSession(): Boolean = accessTokenExpiresAt.isAfter(OffsetDateTime.now())

    fun canRefreshToken(): Boolean = refreshTokenExpiresAt.isAfter(OffsetDateTime.now())

    companion object {
        fun fromSession(session: Session) = SessionModel(
            id = session.id,
            createdAt = session.createdAt,
            projectId = session.projectId,
            userId = session.userId,
            accessToken = session.accessToken,
            accessTokenExpiresAt = session.accessTokenExpiresAt,
            refreshToken = session.refreshToken,
            refreshTokenExpiresAt = session.refreshTokenExpiresAt,
        )
    }
}
