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
import com.usewalletkit.sdk.generated.models.WalletControlMode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 
 *
 * @param network 
 * @param name 
 * @param controlMode 
 * @param ownerId An ID that you can use to associate the wallet with a user in your application. e.g. user uuid. When creating wallets for the same owner_id across different networks, the same seed phrase will be used.
 * @param developerSecret On testnet, the developer_secret is always \"testnet-secret\". On mainnet, the developer_secret is set by the developer during account activation.
 * @param userPin A 6-digit numeric pin that is only known to the user, set during wallet creation. This pin is required to sign transactions from this wallet.
 */
@Parcelize


data class CreateWalletRequest (

    @Json(name = "network")
    val network: Network,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "control_mode")
    val controlMode: WalletControlMode,

    /* An ID that you can use to associate the wallet with a user in your application. e.g. user uuid. When creating wallets for the same owner_id across different networks, the same seed phrase will be used. */
    @Json(name = "owner_id")
    val ownerId: kotlin.String? = null,

    /* On testnet, the developer_secret is always \"testnet-secret\". On mainnet, the developer_secret is set by the developer during account activation. */
    @Json(name = "developer_secret")
    val developerSecret: kotlin.String? = null,

    /* A 6-digit numeric pin that is only known to the user, set during wallet creation. This pin is required to sign transactions from this wallet. */
    @Json(name = "user_pin")
    val userPin: kotlin.String? = null

) : Parcelable

