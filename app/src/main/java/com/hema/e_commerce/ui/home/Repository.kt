package com.hema.e_commerce.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Repository {
    //Live data
    val brandsLiveData = MutableLiveData<BrandsResponce>()
    val onSaleProductsList = MutableLiveData<ProductsResponse>()
    val onHomeProductsList = MutableLiveData<ProductsResponse>()

    //Remote function
    suspend fun getBrands() = RetrofitInstance.api.getBrands()

    suspend  fun getOnSaleProductsList()=RetrofitInstance.api.getOnSaleProductsList()

    suspend  fun getOnHomeProductsList()=RetrofitInstance.api.getOnHomeProductsList()

    //Methods use in viewModel
    fun getBrand() {
        GlobalScope.launch(Dispatchers.IO) {
              val response = getBrands()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("checkkkk", "onResponse: ${it}")
                        Log.i("checkkkk", "getBrands " + it.smart_collections.size)
                        brandsLiveData.value = it
                        Log.i("checkkkk", "getBrands: " + it.smart_collections[0].title)

                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody()) }
            }

        }
    }

    fun getOnSaleProducts(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = getOnSaleProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("checkkkk", "onResponse: ${it}")
                        Log.i("checkkkk", "getBrands " + it.products.size)
                        onSaleProductsList.value = it
                        Log.i("checkkkk", "getBrands: " + it.products[0].title)

                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody()) }
            }

        }
    }
    fun getOnHomeProducts(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = getOnHomeProductsList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("checkkkk", "onResponse: ${it}")
                        Log.i("checkkkk", "getBrands " + it.products.size)
                        onHomeProductsList.value = it
                        Log.i("checkkkk", "getBrands: " + it.products[0].title)

                    }
                } else {
                    Log.i("checkkkk", "getBrands: error " + response.errorBody()) }
            }

        }

    }


}