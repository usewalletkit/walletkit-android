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
 * @param name 
 * @param symbol 
 * @param decimals Specify how many decimals places a token has. E.g. To be able to transfer 1.5 XYZ token, decimals must be at least 1 since that number has a single decimal places.
 * @param initialSupply The amount of tokens to be minted to the token creator.
 * @param displayDecimals Decimals to display on WalletKit dashboard.
 * @param logoUrl url of the token logo.
 * @param developerSecret On testnet, the developer_secret is always \"testnet-secret\". On mainnet, the developer_secret is set by the developer during account activation.
 * @param userPin A 6-digit numeric pin that is only known to the user, set during  wallet creation. This pin is required to sign transactions from  this wallet.
 */
@Parcelize


data class TokensCreateRequest (

    @Json(name = "network")
    val network: Network,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "symbol")
    val symbol: kotlin.String,

    /* Specify how many decimals places a token has. E.g. To be able to transfer 1.5 XYZ token, decimals must be at least 1 since that number has a single decimal places. */
    @Json(name = "decimals")
    val decimals: kotlin.String,

    /* The amount of tokens to be minted to the token creator. */
    @Json(name = "initial_supply")
    val initialSupply: kotlin.String,

    /* Decimals to display on WalletKit dashboard. */
    @Json(name = "display_decimals")
    val displayDecimals: kotlin.String? = null,

    /* url of the token logo. */
    @Json(name = "logo_url")
    val logoUrl: kotlin.String? = null,

    /* On testnet, the developer_secret is always \"testnet-secret\". On mainnet, the developer_secret is set by the developer during account activation. */
    @Json(name = "developer_secret")
    val developerSecret: kotlin.String? = null,

    /* A 6-digit numeric pin that is only known to the user, set during  wallet creation. This pin is required to sign transactions from  this wallet. */
    @Json(name = "user_pin")
    val userPin: kotlin.String? = null

) : Parcelable

