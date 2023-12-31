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

import com.usewalletkit.sdk.generated.models.Nft
import com.usewalletkit.sdk.generated.models.Token

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 
 *
 * @param amount 
 * @param rawAmount 
 * @param from 
 * @param to 
 * @param token 
 * @param nft 
 */
@Parcelize


data class AssetChange (

    @Json(name = "amount")
    val amount: kotlin.String,

    @Json(name = "raw_amount")
    val rawAmount: kotlin.String,

    @Json(name = "from")
    val from: kotlin.String,

    @Json(name = "to")
    val to: kotlin.String,

    @Json(name = "token")
    val token: Token? = null,

    @Json(name = "nft")
    val nft: Nft? = null

) : Parcelable

