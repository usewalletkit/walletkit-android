package com.usewalletkit.sdk.openapi.apis

import com.usewalletkit.sdk.openapi.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.usewalletkit.sdk.openapi.models.LoginWithEmailResponse
import com.usewalletkit.sdk.openapi.models.Session
import com.usewalletkit.sdk.openapi.models.UsersLoginWithEmailRequest
import com.usewalletkit.sdk.openapi.models.UsersRefreshTokenRequest
import com.usewalletkit.sdk.openapi.models.UsersVerifyLoginRequest

interface UsersApi {
    /**
     * Login Anonymously
     * Create an anonymous user session
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @return [Session]
     */
    @POST("users/login-anonymously")
    suspend fun usersLoginAnonymously(): Response<Session>

    /**
     * Login with Email
     * Log a user in with their email.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param usersLoginWithEmailRequest 
     * @return [LoginWithEmailResponse]
     */
    @POST("users/login-with-email")
    suspend fun usersLoginWithEmail(@Body usersLoginWithEmailRequest: UsersLoginWithEmailRequest): Response<LoginWithEmailResponse>

    /**
     * Refresh Token
     * Refresh an access token
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param usersRefreshTokenRequest 
     * @return [Session]
     */
    @POST("users/refresh-token")
    suspend fun usersRefreshToken(@Body usersRefreshTokenRequest: UsersRefreshTokenRequest): Response<Session>

    /**
     * Verify Login
     * Verify a user&#39;s login code.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param usersVerifyLoginRequest 
     * @return [Session]
     */
    @POST("users/verify-login")
    suspend fun usersVerifyLogin(@Body usersVerifyLoginRequest: UsersVerifyLoginRequest): Response<Session>

}
