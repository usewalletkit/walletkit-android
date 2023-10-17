package com.usewalletkit.sdk.auth

import com.usewalletkit.sdk.openapi.apis.UsersApi
import com.usewalletkit.sdk.openapi.models.Session
import com.usewalletkit.sdk.openapi.models.UsersRefreshTokenRequest
import kotlinx.coroutines.runBlocking


class SessionManager(
    private val sessionStore: SessionStore,
    private val usersApi: UsersApi,
) {

    fun hasValidSession(): Boolean = getSession()?.hasValidSession() ?: false

    fun canRefreshToken(): Boolean = getSession()?.canRefreshToken() ?: false

    fun isLoggedIn(): Boolean = hasValidSession() || canRefreshToken()

    fun shouldRefreshToken(): Boolean = !hasValidSession() && canRefreshToken()

    fun storeSession(session: Session) =
        sessionStore.storeSession(SessionModel.fromSession(session))

    fun getAuthToken(): String = getSession()!!.accessToken

    fun refreshToken() {
        runBlocking {
            usersApi.usersRefreshToken(
                usersRefreshTokenRequest = UsersRefreshTokenRequest(
                    sessionId = getSessionId(),
                    refreshToken = getRefreshToken()
                ),
            )
        }
    }

    fun logout() = sessionStore.deleteSession()

    private fun getRefreshToken(): String = getSession()!!.refreshToken

    private fun getSessionId(): String = getSession()!!.id

    private fun getSession(): SessionModel? = sessionStore.getSession()
}
