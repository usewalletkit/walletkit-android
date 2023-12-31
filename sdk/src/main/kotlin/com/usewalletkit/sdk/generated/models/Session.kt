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
 * @param id 
 * @param createdAt 
 * @param projectId 
 * @param userId 
 * @param accessToken 
 * @param accessTokenExpiresAt 
 * @param refreshToken 
 * @param refreshTokenExpiresAt 
 */
@Parcelize


data class Session (

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "created_at")
    val createdAt: java.time.OffsetDateTime,

    @Json(name = "project_id")
    val projectId: kotlin.String,

    @Json(name = "user_id")
    val userId: kotlin.String,

    @Json(name = "access_token")
    val accessToken: kotlin.String,

    @Json(name = "access_token_expires_at")
    val accessTokenExpiresAt: java.time.OffsetDateTime,

    @Json(name = "refresh_token")
    val refreshToken: kotlin.String,

    @Json(name = "refresh_token_expires_at")
    val refreshTokenExpiresAt: java.time.OffsetDateTime

) : Parcelable

