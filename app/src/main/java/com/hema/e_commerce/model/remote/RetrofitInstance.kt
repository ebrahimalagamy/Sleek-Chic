package com.hema.e_commerce.model.remote

import com.google.gson.GsonBuilder

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
            var gson = GsonBuilder()
                .setLenient()
                .create()
         //   val logging = HttpLoggingInterceptor()
         //   logging.setLevel(HttpLoggingInterceptor.Level.BODY)


            val client = OkHttpClient.Builder()
                .addInterceptor(BasciInterceptor("f36da23eb91a2fd4cba11b9a30ff124f",
                    "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"))
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
