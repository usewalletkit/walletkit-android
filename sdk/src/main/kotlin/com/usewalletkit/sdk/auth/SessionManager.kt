package com.usewalletkit.sdk.auth

import com.usewalletkit.sdk.openapi.models.Session


class SessionManager(
    private val sessionStore: SessionStore,
) {

    fun hasValidSession(): Boolean = getSession()?.hasValidSession() ?: false

    fun canRefreshToken(): Boolean = getSession()?.canRefreshToken() ?: false

    fun storeSession(session: Session) =
        sessionStore.storeSession(SessionModel.fromSession(session))

    fun getAuthToken(): String = getSession()!!.accessToken

    private fun getSession(): SessionModel? = sessionStore.getSession()
}
