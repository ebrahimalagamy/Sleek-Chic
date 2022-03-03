package com.hema.e_commerce.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    val repository:Repository= Repository()
    var brandsLiveData = MutableLiveData<BrandsResponce>()
    var onSaleProducts = MutableLiveData<ProductsResponse>()
    var onHomeProducts = MutableLiveData<ProductsResponse>()

    fun getBrand(){
        repository.getBrand()
        brandsLiveData = repository.brandsLiveData

    }
    fun getOnSaleProducts(){
     repository.getOnSaleProducts()
        onSaleProducts=repository.onSaleProductsList
    }
    fun getOnHomeProducts(){
        repository.getOnHomeProducts()
        onHomeProducts=repository.onHomeProductsList

    }


}

