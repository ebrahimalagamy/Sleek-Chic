package com.hema.e_commerce.ui.category.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {


    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ce751b18c7156bf720ea405ad19614f4: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@itiana.myshopify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiService {
        return getInstance().create(ApiService::class.java)
    }


}