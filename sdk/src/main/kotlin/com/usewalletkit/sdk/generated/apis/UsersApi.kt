package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.usewalletkit.sdk.generated.models.ErrorResponse
import com.usewalletkit.sdk.generated.models.LoginWithEmailResponse
import com.usewalletkit.sdk.generated.models.LoginWithWalletResponse
import com.usewalletkit.sdk.generated.models.Session
import com.usewalletkit.sdk.generated.models.SessionChallenge
import com.usewalletkit.sdk.generated.models.UsersLoginWithEmailRequest
import com.usewalletkit.sdk.generated.models.UsersLoginWithPasskeyRequest
import com.usewalletkit.sdk.generated.models.UsersLoginWithWalletRequest
import com.usewalletkit.sdk.generated.models.UsersRefreshTokenRequest
import com.usewalletkit.sdk.generated.models.UsersVerifyLoginRequest

interface UsersApi {
    /**
     * Begin Passkey Registration
     * Begin the passkey registration process
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @return [kotlin.Any]
     */
    @POST("users/passkey/begin-registration")
    suspend fun usersBeginPasskeyRegistration(): Response<kotlin.Any>

    /**
     * Create Session Challenge
     * Create a session challenge for a user
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @return [SessionChallenge]
     */
    @POST("users/create-session-challenge")
    suspend fun usersCreateSessionChallenge(): Response<SessionChallenge>

    /**
     * Finish Passkey Registration
     * Finish the passkey registration process
     * Responses:
     *  - 204: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param body 
     * @return [Unit]
     */
    @POST("users/passkey/finish-registration")
    suspend fun usersFinishPasskeyRegistration(@Body body: kotlin.Any): Response<Unit>

    /**
     * Get Session Challenge
     * Get a session challenge by code
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param code 
     * @return [SessionChallenge]
     */
    @GET("users/session-challenge")
    suspend fun usersGetSessionChallenge(@Query("code") code: kotlin.String): Response<SessionChallenge>

    /**
     * Login Anonymously
     * Create an anonymous user session
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @return [Session]
     */
    @POST("users/login-anonymously")
    suspend fun usersLoginAnonymously(): Response<Session>

    /**
     * Login with Discoverable Passkey
     * Log a user in with a discoverable passkey
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @return [kotlin.Any]
     */
    @POST("users/login-with-discoverable-passkey")
    suspend fun usersLoginWithDiscoverablePasskey(): Response<kotlin.Any>

    /**
     * Login with Email
     * Log a user in with their email.
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param usersLoginWithEmailRequest 
     * @return [LoginWithEmailResponse]
     */
    @POST("users/login-with-email")
    suspend fun usersLoginWithEmail(@Body usersLoginWithEmailRequest: UsersLoginWithEmailRequest): Response<LoginWithEmailResponse>

    /**
     * Login with Magic Link
     * Log a user in with their email and magic link
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param usersLoginWithEmailRequest 
     * @return [SessionChallenge]
     */
    @POST("users/login-with-magic-link")
    suspend fun usersLoginWithMagicLink(@Body usersLoginWithEmailRequest: UsersLoginWithEmailRequest): Response<SessionChallenge>

    /**
     * Login with Passkey
     * Log a user in with a passkey
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param usersLoginWithPasskeyRequest 
     * @return [kotlin.Any]
     */
    @POST("users/login-with-passkey")
    suspend fun usersLoginWithPasskey(@Body usersLoginWithPasskeyRequest: UsersLoginWithPasskeyRequest): Response<kotlin.Any>

    /**
     * Login with Wallet
     * Log a user in with their wallet using Sign in with Ethereum.
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param usersLoginWithWalletRequest 
     * @return [LoginWithWalletResponse]
     */
    @POST("users/login-with-wallet")
    suspend fun usersLoginWithWallet(@Body usersLoginWithWalletRequest: UsersLoginWithWalletRequest): Response<LoginWithWalletResponse>

    /**
     * Logout
     * Log a user out and clear session cookies (only available in SDKs)
     * Responses:
     *  - 204: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @return [Unit]
     */
    @POST("users/logout")
    suspend fun usersLogout(): Response<Unit>

    /**
     * Refresh Token
     * Refresh an access token
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param usersRefreshTokenRequest 
     * @return [Session]
     */
    @POST("users/refresh-token")
    suspend fun usersRefreshToken(@Body usersRefreshTokenRequest: UsersRefreshTokenRequest): Response<Session>

    /**
     * Verify Login
     * Verify a user&#39;s login challenge and create a session.
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param usersVerifyLoginRequest 
     * @return [Session]
     */
    @POST("users/verify-login")
    suspend fun usersVerifyLogin(@Body usersVerifyLoginRequest: UsersVerifyLoginRequest): Response<Session>

}
