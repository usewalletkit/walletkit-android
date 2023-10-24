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
 * @param from from address of the transfer
 * @param contractAddress collection contract address
 * @param recipient recipient of the NFT transfer
 * @param tokenId 
 * @param developerSecret On testnet, the developer_secret is always \"testnet-secret\". On mainnet, the developer_secret is set by the developer during account activation.
 * @param userPin A 6-digit numeric pin that is only known to the user, set during  wallet creation. This pin is required to sign transactions from  this wallet.
 */
@Parcelize


data class NftsTransferNftRequest (

    @Json(name = "network")
    val network: Network,

    /* from address of the transfer */
    @Json(name = "from")
    val from: kotlin.String,

    /* collection contract address */
    @Json(name = "contract_address")
    val contractAddress: kotlin.String,

    /* recipient of the NFT transfer */
    @Json(name = "recipient")
    val recipient: kotlin.String,

    @Json(name = "token_id")
    val tokenId: kotlin.String? = null,

    /* On testnet, the developer_secret is always \"testnet-secret\". On mainnet, the developer_secret is set by the developer during account activation. */
    @Json(name = "developer_secret")
    val developerSecret: kotlin.String? = null,

    /* A 6-digit numeric pin that is only known to the user, set during  wallet creation. This pin is required to sign transactions from  this wallet. */
    @Json(name = "user_pin")
    val userPin: kotlin.String? = null

) : Parcelable

