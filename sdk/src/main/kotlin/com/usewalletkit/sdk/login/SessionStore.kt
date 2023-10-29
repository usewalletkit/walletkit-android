package com.usewalletkit.sdk.login

interface SessionStore {
    fun storeSession(session: SessionModel)
    fun getSession(): SessionModel?
    fun deleteSession()
}
