package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.usewalletkit.sdk.generated.models.ChangeUserPinRequest
import com.usewalletkit.sdk.generated.models.ChangeUserPinResponse
import com.usewalletkit.sdk.generated.models.CreateWalletRequest
import com.usewalletkit.sdk.generated.models.ErrorResponse
import com.usewalletkit.sdk.generated.models.ExportWalletRequest
import com.usewalletkit.sdk.generated.models.ExportWalletResponse
import com.usewalletkit.sdk.generated.models.Network
import com.usewalletkit.sdk.generated.models.Wallet

interface WalletsApi {
    /**
     * Change User Pin
     * Change pin for user controlled wallets
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param changeUserPinRequest 
     * @return [ChangeUserPinResponse]
     */
    @POST("wallets/change-user-pin")
    suspend fun walletsChangeUserPin(@Body changeUserPinRequest: ChangeUserPinRequest): Response<ChangeUserPinResponse>

    /**
     * Create Wallet
     * Creates a wallet with provided metadata.
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param createWalletRequest 
     * @return [Wallet]
     */
    @POST("wallets")
    suspend fun walletsCreate(@Body createWalletRequest: CreateWalletRequest): Response<Wallet>

    /**
     * Export Wallet
     * Export wallet mnemonic phrase
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param exportWalletRequest 
     * @return [ExportWalletResponse]
     */
    @POST("wallets/export")
    suspend fun walletsExport(@Body exportWalletRequest: ExportWalletRequest): Response<ExportWalletResponse>

    /**
     * Get Wallet by Address
     * Get a wallet by network and address
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param network 
     * @param address 
     * @return [Wallet]
     */
    @GET("wallets/get-by-address")
    suspend fun walletsGetByAddress(@Query("network") network: Network, @Query("address") address: kotlin.String): Response<Wallet>

    /**
     * Get Wallet by ID
     * Get a wallet by id
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param network 
     * @param id 
     * @return [Wallet]
     */
    @GET("wallets/get-by-id")
    suspend fun walletsGetById(@Query("network") network: Network, @Query("id") id: kotlin.String): Response<Wallet>

    /**
     * Get User Wallet by Network
     * Get a wallet for a user by network
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param network 
     * @return [Wallet]
     */
    @GET("wallets/get-by-network")
    suspend fun walletsGetByNetwork(@Query("network") network: Network): Response<Wallet>

    /**
     * Get Wallet by Owner ID
     * Get a wallet by network and owner id
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param network 
     * @param ownerID 
     * @return [Wallet]
     */
    @GET("wallets/get-by-owner-id")
    suspend fun walletsGetByOwnerId(@Query("network") network: Network, @Query("ownerID") ownerID: kotlin.String): Response<Wallet>

    /**
     * List Wallets
     * Lists wallets created in the project. Supports filter by network.
     * Responses:
     *  - 200: 
     *  - 400: 
     *  - 401: 
     *  - 403: 
     *  - 500: 
     *
     * @param network  (optional)
     * @param page  (optional)
     * @param pageSize  (optional)
     * @return [kotlin.collections.List<Wallet>]
     */
    @GET("wallets")
    suspend fun walletsList(@Query("network") network: Network? = null, @Query("page") page: kotlin.Int? = null, @Query("page_size") pageSize: kotlin.String? = null): Response<kotlin.collections.List<Wallet>>

}
