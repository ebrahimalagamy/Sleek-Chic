package com.hema.e_commerce.model.remote.currancynetwork

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrancyRetrofitClient {


    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.exchangerate.host/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiService {
        return getInstance().create(ApiService::class.java)
    }





}