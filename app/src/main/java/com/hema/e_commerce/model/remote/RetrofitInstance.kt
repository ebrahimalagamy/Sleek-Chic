package com.hema.e_commerce.model.remote

import com.hema.e_commerce.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        //lazy mean we initialize it once
        private val retrofit by lazy {
      /*   // in addInterceptor to see call in run
              val logging = HttpLoggingInterceptor()
           logging.setLevel(HttpLoggingInterceptor.Level.BODY)*/

            val client = OkHttpClient.Builder()
                .addInterceptor(BasciInterceptor("bfe73f4cd7e7f8737d5928b2a439022e","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"))
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy {
            //write class response
              retrofit.create( ShopifyApi::class.java)
        }

    }

}
