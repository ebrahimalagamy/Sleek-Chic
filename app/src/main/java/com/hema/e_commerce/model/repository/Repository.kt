package com.hema.e_commerce.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.R
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.remote.RetrofitInstance
import com.hema.e_commerce.ui.cart.CartData
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.ui.category.testmodels.SubCollectionResponse
import com.hema.e_commerce.ui.category.testmodels.SubCollections
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
            val response = RetrofitInstance.api.getAllCollections()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse: ${it}")
                        Log.i(TAG, "getCollections:size " + it.custom_collections.size)
                        collectionsLiveData.value = it
                        Log.i("TAG", "getCollections: " + it.custom_collections.size)

                    }
                } else {
                    Log.i(TAG, "getCollections: error " + response.errorBody())


                }
            }

        }
    }


    val collectionProductsLiveData = MutableLiveData<ProductsResponse>()

    fun getSubCollectionsProducts(categoryId: Long) {

        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getCollectionProducts(categoryId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse: ${it}")
                        collectionProductsLiveData.value = it

                    }
                } else {
                    Log.i(TAG, "getProducts: error " + response.errorBody())


                }
            }

        }
    }

//sub collections

    var subCollectionProductsLiveData = MutableLiveData<ArrayList<SubCollections>>()

    fun getSubCollections(position: Int) {
        subCollectionProductsLiveData.value = ShowSubCollections().showSub(position)
    }

//Single product

    val singleProductsLiveData = MutableLiveData<Product>()
    fun getSingleProduct(productId: Long) {

        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getSingleProduct(productId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d(TAG, "onResponse single: ${it}")
                        singleProductsLiveData.value = it

                    }
                } else {
                    Log.i(TAG, "getProduct: error " + response.errorBody())


                }
            }

        }
    }




//
    //for cart adapter
    //class CartData(var cartIcon:Int,var cartDesc:String,var cartPrice:String,var cartCopoun:String)
    val arrayList = arrayListOf<CartData>(
        CartData(R.drawable.dress, "dress", "33$", "Deliver for free"),
        CartData(R.drawable.dress, "dress", "43$", "Deliver for free"),
        CartData(R.drawable.dress, "dress", "55$", "Deliver for free")


    )
}