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

package com.usewalletkit.sdk.openapi.models

import com.usewalletkit.sdk.openapi.models.Network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 
 *
 * @param uuid 
 * @param network 
 * @param contractAddress 
 * @param name 
 * @param symbol 
 * @param decimals 
 * @param displayDecimals 
 * @param logoUrl 
 * @param balance 
 */
@Parcelize


data class ListTokenBalancesResponseItem (

    @Json(name = "uuid")
    val uuid: kotlin.String,

    @Json(name = "network")
    val network: Network,

    @Json(name = "contract_address")
    val contractAddress: kotlin.String,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "symbol")
    val symbol: kotlin.String,

    @Json(name = "decimals")
    val decimals: kotlin.Int,

    @Json(name = "display_decimals")
    val displayDecimals: kotlin.Int,

    @Json(name = "logo_url")
    val logoUrl: kotlin.String,

    @Json(name = "Balance")
    val balance: kotlin.String

) : Parcelable
