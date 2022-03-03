package com.hema.e_commerce.model.remote

import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ShopifyApi {
    //@GET / @POST FUNCTIONS

    @GET("custom_collections.json")
    suspend fun getAllCollections(): Response<CustomCollectionsResponse>

    @GET("smart_collections.json")
    suspend fun getBrands(): Response<BrandsResponce>

    @GET("products.json")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("collections/{collection_id}/products.json")
    suspend fun getCollectionProducts(@Path("collection_id") collection_id:Long):Response<ProductsResponse>


}