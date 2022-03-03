package com.hema.e_commerce.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Repository {
    //Live data
    val brandsLiveData = MutableLiveData<BrandsResponce>()

    //Remote function
    suspend fun getBrands() = RetrofitInstance.api.getBrands()

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

    fun getBrandLiveData():MutableLiveData<BrandsResponce> {
        return brandsLiveData
    }


}