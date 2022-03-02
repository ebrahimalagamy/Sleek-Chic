package com.hema.e_commerce.model.remote

import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import retrofit2.Response
import retrofit2.http.GET


interface ShopifyApi {
    //@GET / @POST FUNCTIONS
    @GET("custom_collections.json")
    suspend fun getAllCollections(): Response<CustomCollectionsResponse>

}