package com.hema.e_commerce.ui.category.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.R
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.ui.cart.CartData
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.ui.category.testmodels.ModelContainer
import com.hema.e_commerce.ui.category.testmodels.TypeModelList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository {


    private val TAG = "CollectionsRepo"
    val collectionsLiveData = MutableLiveData<CustomCollectionsResponse>()
    fun getCollections() {


        GlobalScope.launch(Dispatchers.IO) {
            val response =RetrofitInstance.api.getAllCollections()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse: ${it}")
                        Log.i(TAG, "getCollections:size " +it.custom_collections.size)
                        collectionsLiveData.value = it
                        Log.i("TAG", "getCollections: " + it.custom_collections.size)

                    }
                } else {
                    Log.i(TAG, "getCollections: error " + response.errorBody())


                }
            }

        }
    }








    // for container
    val arrContainer = arrayListOf<ModelContainer>(
        ModelContainer("man", R.drawable.test2),
        ModelContainer("woman", R.drawable.test2),
        ModelContainer("bags", R.drawable.test2),
        ModelContainer("dresses", R.drawable.test2),
        ModelContainer("suits", R.drawable.test2),
        ModelContainer("shose", R.drawable.test2),
        ModelContainer("accessories", R.drawable.test2),
        ModelContainer("hair", R.drawable.test2)
    )

// for type list


    val arrTypeList = arrayListOf<TypeModelList>(
        TypeModelList("man", "shirt black red ", R.drawable.test2),
        TypeModelList("woman", "dress black red ", R.drawable.test2),
        TypeModelList("bags", "bag black red", R.drawable.test2),
        TypeModelList("dresses", "dress black red ", R.drawable.test2),
        TypeModelList("suits", "suits black red", R.drawable.test2),
        TypeModelList("shose", "shose black red ", R.drawable.test2),
        TypeModelList("accessories", "accessories black red", R.drawable.test2),
        TypeModelList("hair", "hair black red", R.drawable.test2)
    )

    //for cart adapter
    //class CartData(var cartIcon:Int,var cartDesc:String,var cartPrice:String,var cartCopoun:String)
    val arrayList = arrayListOf<CartData>(
        CartData(R.drawable.dress, "dress", "33$", "Deliver for free"),
        CartData(R.drawable.dress, "dress", "43$", "Deliver for free"),
        CartData(R.drawable.dress, "dress", "55$", "Deliver for free")


    )
}