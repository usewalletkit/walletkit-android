package com.usewalletkit.sdk.login

import com.usewalletkit.sdk.generated.apis.UsersApi
import com.usewalletkit.sdk.generated.models.Session


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

    fun getOwnerId(): String = getSession()!!.userId

    fun getRefreshToken(): String = getSession()!!.refreshToken

    fun getSessionId(): String = getSession()!!.id

    fun logout() = sessionStore.deleteSession()

    private fun getSession(): SessionModel? = sessionStore.getSession()
}
