package com.hema.e_commerce.model.remote

import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.customer.AddressArray
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.model.dataclass.customer.CustomersModel
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.dataclass.test.Order
import com.hema.e_commerce.model.dataclass.test.OrderResponce
import com.hema.e_commerce.model.dataclass.currancy.CurrancyResponse
import retrofit2.Response
import retrofit2.http.*


interface ShopifyApi {
    //@GET / @POST FUNCTIONS

    @GET("custom_collections.json")
    suspend fun getAllCollections(): Response<CustomCollectionsResponse>

    @GET("products.json")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("collections/{collection_id}/products.json")
    suspend fun getCollectionProducts(@Path("collection_id") collection_id: Long): Response<ProductsResponse>

    @GET("smart_collections.json")
    suspend fun getBrands(): Response<BrandsResponce>

    @GET("collections/398034665703/products.json")
    suspend fun getOnSaleProductsList(): Response<ProductsResponse>

    @GET("collections/398033617127/products.json")
    suspend fun getOnHomeProductsList(): Response<ProductsResponse>

    //retrieve Single product
    @GET("products/{product_id}.json")
    suspend fun getSingleProduct(@Path("product_id") product_id: Long): Response<ProductCollectionResponse>

    // customer get/post/put
    @POST("customers.json")
    suspend fun register(@Body customer: CustomerModel):
            Response<CustomerModel>

    @GET("customers.json")
    suspend fun login(): Response<CustomersModel>

    @PUT("customers/{customer_id}.json")
    suspend fun update(@Path("customer_id") customer_id: Long, @Body customer: CustomerModel):
            Response<CustomerModel>

    @POST("customers/{customer_id}/addresses.json")
    suspend fun addAddress(@Path("customer_id") id: Long, @Body address: AddressModel):
            Response<AddressModel>

    @PUT("customers/{customer_id}/addresses/{address_id}.json")
    suspend fun updateAddress(
        @Path("customer_id") customerId: Long,
        @Path("address_id") addressId: Long,
        @Body address: AddressModel
    ):
            Response<AddressModel>

    @DELETE("customers/{customer_id}/addresses/{address_id}.json")
    suspend fun deleteAddress(
        @Path("customer_id") customerId: Long,
        @Path("address_id") addressId: Long
    ):
            Response<AddressModel>

    @GET("customers/{customer_id}/addresses.json")
    suspend fun getAddress(@Path("customer_id") customerId: Long):
            Response<AddressArray>

    @PUT("customers/{customer_id}/addresses/{address_id}/default.json")
    suspend fun setDefault(
        @Path("customer_id") customerId: Long,
        @Path("address_id") addressId: Long
    ): Response<AddressModel>

    //creat an order in api web
    @POST("orders.json")
    suspend fun createOrder(@Body order: Order): Response<OrderResponce>

//    @GET("orders/{order_id}.json")
//    suspend fun getAnOrder(@Path("order_id") id: Long): Response<Order>

    @DELETE("orders/{order_id}.json")
    suspend fun deleteAnOrder(@Path("order_id") id: Long): Response<String>





}

