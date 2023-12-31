package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.usewalletkit.sdk.generated.models.BuyGasResponse
import com.usewalletkit.sdk.generated.models.ErrorResponse
import com.usewalletkit.sdk.generated.models.GasBuyRequest

interface GasApi {
    /**
     * Buy Gas
     * Tops up gas to the destination wallet address (default to project wallet if not provided explicitly).
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param gasBuyRequest 
     * @return [BuyGasResponse]
     */
    @POST("gas/buy")
    suspend fun gasBuy(@Body gasBuyRequest: GasBuyRequest): Response<BuyGasResponse>

}
