package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.usewalletkit.sdk.generated.models.GetTransactionStatusByHashResponse
import com.usewalletkit.sdk.generated.models.GetTransactionStatusByIdResponse
import com.usewalletkit.sdk.generated.models.Network
import com.usewalletkit.sdk.generated.models.TransactionSubmissionResponse
import com.usewalletkit.sdk.generated.models.TransactionsSignAndSendRequest

interface TransactionsApi {
    /**
     * Transaction Status By Hash
     * Retrieves the status of a transaction by its hash.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param network 
     * @param transactionHash 
     * @return [GetTransactionStatusByHashResponse]
     */
    @GET("transactions/status-by-hash")
    suspend fun transactionsGetStatus(@Query("network") network: Network, @Query("transaction_hash") transactionHash: kotlin.String): Response<GetTransactionStatusByHashResponse>

    /**
     * Sign and Send
     * This endpoint allows arbitrary smart contract function calls. It can be used to interact with smart contracts that are not supported by the Token/NFT APIs.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param transactionsSignAndSendRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("transactions/sign-and-send")
    suspend fun transactionsSignAndSend(@Body transactionsSignAndSendRequest: TransactionsSignAndSendRequest): Response<TransactionSubmissionResponse>

    /**
     * Transaction Status by ID
     * Retrieves the status of a transaction by its id returned from the APIs that change the state of blockchain&#x60; (e.g. /tokens/transfer)&#x60;
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param id transaction id returned in POST requests that result in a transaction. e.g. Transfer Token (optional)
     * @return [GetTransactionStatusByIdResponse]
     */
    @GET("transactions/status-by-id")
    suspend fun transactionsStatusById(@Query("id") id: kotlin.String? = null): Response<GetTransactionStatusByIdResponse>

}
