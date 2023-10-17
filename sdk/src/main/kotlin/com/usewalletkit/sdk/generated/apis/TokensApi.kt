package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response

import com.usewalletkit.sdk.generated.models.GetTokenResponse
import com.usewalletkit.sdk.generated.models.ListTokenBalancesResponseItem
import com.usewalletkit.sdk.generated.models.Network
import com.usewalletkit.sdk.generated.models.TokensBatchTransferRequest
import com.usewalletkit.sdk.generated.models.TokensCreateRequest
import com.usewalletkit.sdk.generated.models.TokensMintRequest
import com.usewalletkit.sdk.generated.models.TokensTransferRequest
import com.usewalletkit.sdk.generated.models.TransactionSubmissionResponse

interface TokensApi {
    /**
     * Batch Transfer Token
     * Batch transfers from the &#x60;from&#x60; wallet to the list of  &#x60;recipients&#x60; with the provided amount. The &#x60;from&#x60; address can only be the ones  created in the project. Transfers within a batch are processed atomically  in a single transaction, resulting in significantly lower average costs  compared to individual transfers 
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param tokensBatchTransferRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("tokens/batch-transfer")
    suspend fun tokensBatchTransfer(@Body tokensBatchTransferRequest: TokensBatchTransferRequest): Response<TransactionSubmissionResponse>

    /**
     * Create Token
     * Creates an on-chain token with provided metadata.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param tokensCreateRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("tokens")
    suspend fun tokensCreate(@Body tokensCreateRequest: TokensCreateRequest): Response<TransactionSubmissionResponse>

    /**
     * Get Token
     * Gets the metadata for a token.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param network 
     * @param token token contract address or symbol in case of the gas token
     * @return [GetTokenResponse]
     */
    @GET("tokens/tokens")
    suspend fun tokensGet(@Query("network") network: Network, @Query("token") token: kotlin.String): Response<GetTokenResponse>

    /**
     * List Token Balances
     * Retrieves a list of token balances associated with a specific wallet address on a given network.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param network 
     * @param walletAddress 
     * @return [kotlin.collections.List<ListTokenBalancesResponseItem>]
     */
    @GET("tokens/balances")
    suspend fun tokensListBalances(@Query("network") network: Network, @Query("wallet_address") walletAddress: kotlin.String): Response<kotlin.collections.List<ListTokenBalancesResponseItem>>

    /**
     * Mint Token
     * Mints specified amount of token to the recipient.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param tokensMintRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("tokens/mint")
    suspend fun tokensMint(@Body tokensMintRequest: TokensMintRequest): Response<TransactionSubmissionResponse>

    /**
     * Transfer Token
     * Transfers the specified amount of tokens from &#x60;from&#x60; to &#x60;recipient&#x60;. The from address can only be the ones created in the project.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param tokensTransferRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("tokens/transfer")
    suspend fun tokensTransfer(@Body tokensTransferRequest: TokensTransferRequest): Response<TransactionSubmissionResponse>

}
