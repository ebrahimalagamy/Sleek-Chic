package com.hema.e_commerce.model.remote.currancynetwork

import com.hema.e_commerce.model.dataclass.currancy.CurrancyResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {



    @GET("convert?from=EGP")
    suspend fun changeCurrancy(@Query("to") to: String, @Query("amount") amount: Double):
            Response<CurrancyResponse>


}