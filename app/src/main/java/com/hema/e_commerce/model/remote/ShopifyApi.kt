package com.hema.e_commerce.model.remote

import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.customer.CustomerLoginModel
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path



interface ShopifyApi {
    //@GET / @POST FUNCTIONS

    @GET("custom_collections.json")
    suspend fun getAllCollections(): Response<CustomCollectionsResponse>

    @GET("products.json")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("collections/{collection_id}/products.json")
    suspend fun getCollectionProducts(  @Path("collection_id") collection_id:Long):Response<ProductsResponse>


    @GET("smart_collections.json")
    suspend fun getBrands(): Response<BrandsResponce>

    @GET("collections/398034665703/products.json")
    suspend  fun getOnSaleProductsList(): Response<ProductsResponse>

    @GET("collections/398033617127/products.json")
    suspend  fun getOnHomeProductsList(): Response<ProductsResponse>

    //retrieve Single product
    @GET("products/{product_id}.json")
    suspend fun getSingleProduct( @Path("product_id") product_id:Long):Response<ProductCollectionResponse>

    @POST("customers.json")
    suspend fun register(@Body customer: CustomerModel):
            Response<CustomerModel>

    @GET("customers.json")
    suspend fun login(): Response<CustomerLoginModel>

}

