package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.usewalletkit.sdk.generated.models.ListWalletActivityResponseItem
import com.usewalletkit.sdk.generated.models.Network

interface ActivityApi {
    /**
     * List Wallet Activity
     * Retrieves activity history on a specific wallet address.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param network 
     * @param walletAddress 
     * @param page  (optional)
     * @param pageSize  (optional)
     * @return [kotlin.collections.List<ListWalletActivityResponseItem>]
     */
    @GET("activity")
    suspend fun activityList(@Query("network") network: Network, @Query("wallet_address") walletAddress: kotlin.String, @Query("page") page: kotlin.Int? = null, @Query("page_size") pageSize: kotlin.Int? = null): Response<kotlin.collections.List<ListWalletActivityResponseItem>>

}
