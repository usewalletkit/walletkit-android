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
 * @param transactionId 
 * @param network 
 * @param status 
 * @param transactionHash If the transaction has been sent to the blockchain (status = pending/success/failed), this field will be populated with the transaction hash.
 * @param contractAddress If the transaction is a contract creation, this field will be populated with the contract address.
 */
@Parcelize


data class GetTransactionStatusByIdResponse (

    @Json(name = "transaction_id")
    val transactionId: kotlin.String,

    @Json(name = "network")
    val network: Network,

    @Json(name = "status")
    val status: kotlin.String,

    /* If the transaction has been sent to the blockchain (status = pending/success/failed), this field will be populated with the transaction hash. */
    @Json(name = "transaction_hash")
    val transactionHash: kotlin.String? = null,

    /* If the transaction is a contract creation, this field will be populated with the contract address. */
    @Json(name = "contract_address")
    val contractAddress: kotlin.String? = null

) : Parcelable
