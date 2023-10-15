package com.usewalletkit.sdk.auth

interface SessionStore {
    fun storeSession(session: SessionModel)
    fun getSession(): SessionModel?
}
