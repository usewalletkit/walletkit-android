/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.usewalletkit.sdk.generated.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 
 *
 * @param userId 
 * @param verificationCode 
 * @param siweMessage 
 * @param signature 
 * @param sessionChallengeCode 
 * @param passkeyCredentialAssertionResponse 
 */
@Parcelize


data class UsersVerifyLoginRequest (

    @Json(name = "user_id")
    val userId: kotlin.String? = null,

    @Json(name = "verification_code")
    val verificationCode: kotlin.String? = null,

    @Json(name = "siwe_message")
    val siweMessage: kotlin.String? = null,

    @Json(name = "signature")
    val signature: kotlin.String? = null,

    @Json(name = "session_challenge_code")
    val sessionChallengeCode: kotlin.String? = null,

    @Json(name = "passkey_credential_assertion_response")
    val passkeyCredentialAssertionResponse: kotlin.String? = null

) : Parcelable

