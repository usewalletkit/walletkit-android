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

import com.usewalletkit.sdk.generated.models.Network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 
 *
 * @param network 
 * @param address 
 * @param userPin A 6-digit numeric pin that is only known to the user, set during wallet creation. This pin is required to sign transactions from this wallet.
 */
@Parcelize


data class ExportWalletRequest (

    @Json(name = "network")
    val network: Network,

    @Json(name = "address")
    val address: kotlin.String,

    /* A 6-digit numeric pin that is only known to the user, set during wallet creation. This pin is required to sign transactions from this wallet. */
    @Json(name = "user_pin")
    val userPin: kotlin.String?

) : Parcelable

