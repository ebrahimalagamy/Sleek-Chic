package com.hema.e_commerce.model.remote.currancynetwork

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.hema.e_commerce.model.dataclass.currancy.CurrancyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CurrancyRepository() {
    private val TAG = "CurrancyRepo"
    val currancyLiveData = MutableLiveData<CurrancyResponse>()
    fun getCurrancy(to:String,amount:Double) {


        GlobalScope.launch(Dispatchers.IO) {
            val response = CurrancyRetrofitClient.getApiService()
                .changeCurrancy(to,amount)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse: ${it}")
                        Log.i(TAG, "getCurrancy: " + response.body().toString())
                        currancyLiveData.value = it
                    }
                } else {
                    Log.i(TAG, "getCurrancy: error " + response.errorBody())
                }
            }

        }
    }

}