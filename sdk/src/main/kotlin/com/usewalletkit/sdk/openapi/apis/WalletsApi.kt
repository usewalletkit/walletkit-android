package com.usewalletkit.sdk.openapi.apis

import com.usewalletkit.sdk.openapi.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import com.squareup.moshi.Json

import com.usewalletkit.sdk.openapi.models.ChangeUserPinRequest
import com.usewalletkit.sdk.openapi.models.ChangeUserPinResponse
import com.usewalletkit.sdk.openapi.models.CreateWalletRequest
import com.usewalletkit.sdk.openapi.models.CreateWalletResponse
import com.usewalletkit.sdk.openapi.models.ExportWalletRequest
import com.usewalletkit.sdk.openapi.models.ExportWalletResponse
import com.usewalletkit.sdk.openapi.models.ListWalletsResponseItem
import com.usewalletkit.sdk.openapi.models.Network
import com.usewalletkit.sdk.openapi.models.Wallet

interface WalletsApi {
    /**
     * Change User Pin
     * Change pin for user controlled wallets
     * Responses:
     *  - 200: 
     *  - 400: 
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
     *
     * @param createWalletRequest 
     * @return [CreateWalletResponse]
     */
    @POST("wallets")
    suspend fun walletsCreate(@Body createWalletRequest: CreateWalletRequest): Response<CreateWalletResponse>

    /**
     * Export Wallet
     * Export wallet mnemonic phrase
     * Responses:
     *  - 200: 
     *  - 400: 
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
     *
     * @param network  (optional)
     * @param page  (optional)
     * @param pageSize  (optional)
     * @return [kotlin.collections.List<ListWalletsResponseItem>]
     */
    @GET("wallets")
    suspend fun walletsList(@Query("network") network: Network? = null, @Query("page") page: kotlin.Int? = null, @Query("page_size") pageSize: kotlin.String? = null): Response<kotlin.collections.List<ListWalletsResponseItem>>

}
