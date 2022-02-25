package com.hema.e_commerce.model.remote

import com.hema.e_commerce.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {

  companion object {

      //lazy mean we initialize it once
      private val retrofit by lazy {
          val logging = HttpLoggingInterceptor()
          logging.setLevel(HttpLoggingInterceptor.Level.BODY)

          val client = OkHttpClient.Builder()
              .addInterceptor(logging)
              .build()

          Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .client(client)
              .build()
      }
       val api by lazy {
           //write class response
       //   retrofit.create( ::class.java)
      }

  }

}
