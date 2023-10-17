package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response

import com.usewalletkit.sdk.generated.models.BuyGasResponse
import com.usewalletkit.sdk.generated.models.GasBuyRequest

interface GasApi {
    /**
     * Buy Gas
     * Tops up gas to the destination wallet address (default to project wallet if not provided explicitly).
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param gasBuyRequest 
     * @return [BuyGasResponse]
     */
    @POST("gas/buy")
    suspend fun gasBuy(@Body gasBuyRequest: GasBuyRequest): Response<BuyGasResponse>

}
