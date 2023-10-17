package com.usewalletkit.sdk.generated.apis

import com.usewalletkit.sdk.generated.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response

import com.usewalletkit.sdk.generated.models.GetNftCollectionResponse
import com.usewalletkit.sdk.generated.models.ListNftCollectionsResponseItem
import com.usewalletkit.sdk.generated.models.ListNftsResponseItem
import com.usewalletkit.sdk.generated.models.Network
import com.usewalletkit.sdk.generated.models.NftImageUploadResponse
import com.usewalletkit.sdk.generated.models.NftsCreateRequest
import com.usewalletkit.sdk.generated.models.NftsMintNftRequest
import com.usewalletkit.sdk.generated.models.NftsTransferNftRequest
import com.usewalletkit.sdk.generated.models.TransactionSubmissionResponse

interface NftsApi {
    /**
     * Create NFT Collection
     * Creates an NFT Collection with provided metadata. An NFT collection refers to a group of non-fungible tokens (NFTs) that are created and released together, often following a specific theme or concept. NFTs are unique digital assets that are stored on a blockchain, represent ownership or proof of authenticity of a particular item, artwork, or digital content. The created nft collection&#39;s address can be found by &#x60;/transactions/status-by-id&#x60; endpoint once the transaction is succeeded. 
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param nftsCreateRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("nfts/collections")
    suspend fun nftsCreate(@Body nftsCreateRequest: NftsCreateRequest): Response<TransactionSubmissionResponse>

    /**
     * Get NFT Collection
     * Get NFT collection by collection address
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param network  (optional)
     * @param collectionAddress address of the NFT contract (optional)
     * @return [GetNftCollectionResponse]
     */
    @GET("nfts/collection-by-address")
    suspend fun nftsGetNftCollection(@Query("network") network: Network? = null, @Query("collection_address") collectionAddress: kotlin.String? = null): Response<GetNftCollectionResponse>

    /**
     * List NFT Collections
     * List NFT collections owned by wallet
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param network 
     * @param walletAddress 
     * @param page page number starting from 1 (optional)
     * @param pageSize size of each page (optional)
     * @return [kotlin.collections.List<ListNftCollectionsResponseItem>]
     */
    @GET("nfts/collections")
    suspend fun nftsListNftCollections(@Query("network") network: Network, @Query("wallet_address") walletAddress: kotlin.String, @Query("page") page: kotlin.Int? = null, @Query("page_size") pageSize: kotlin.Int? = null): Response<kotlin.collections.List<ListNftCollectionsResponseItem>>

    /**
     * List NFTs
     * List NFTs by wallet or by collection address
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param network 
     * @param walletAddress query by wallet address. If provided, contract_address should not be set. (optional)
     * @param contractAddress query by collection address. If provided, wallet_address should not be set. (optional)
     * @param page page number, starting from 1 (optional)
     * @param pageSize size of each page (optional)
     * @return [kotlin.collections.List<ListNftsResponseItem>]
     */
    @GET("nfts")
    suspend fun nftsListNfts(@Query("network") network: Network, @Query("wallet_address") walletAddress: kotlin.String? = null, @Query("contract_address") contractAddress: kotlin.String? = null, @Query("page") page: kotlin.Int? = null, @Query("page_size") pageSize: kotlin.Int? = null): Response<kotlin.collections.List<ListNftsResponseItem>>

    /**
     * Mint NFT
     * Mints a NFT from a collection
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param nftsMintNftRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("nfts/mint")
    suspend fun nftsMintNft(@Body nftsMintNftRequest: NftsMintNftRequest): Response<TransactionSubmissionResponse>

    /**
     * Upload NFT image
     * Upload NFT image to IPFS
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @return [NftImageUploadResponse]
     */
    @POST("nfts/upload")
    suspend fun nftsNftImageUpload(): Response<NftImageUploadResponse>

    /**
     * Transfer NFT
     * Transfers NFT from &#x60;from&#x60; to &#x60;recipient&#x60;. The from address must be wallet created in the project.
     * Responses:
     *  - 200: 
     *  - 400: 
     *
     * @param nftsTransferNftRequest 
     * @return [TransactionSubmissionResponse]
     */
    @POST("nfts/transfer")
    suspend fun nftsTransferNft(@Body nftsTransferNftRequest: NftsTransferNftRequest): Response<TransactionSubmissionResponse>

}
