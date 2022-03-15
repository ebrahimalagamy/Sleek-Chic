package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.dataclass.smartCollection.BrandsResponce
import com.hema.e_commerce.model.repository.Repository

class HomeViewModel(private val repository: Repository, app: Application) : AndroidViewModel(app) {
    var brandsLiveData = MutableLiveData<BrandsResponce>()
    var onSaleProducts = MutableLiveData<ProductsResponse>()
    var onHomeProducts = MutableLiveData<ProductsResponse>()

    fun getBrand() {
        repository.getBrand()
        brandsLiveData = repository.brandsLiveData
    }

    fun getFavProducts(customerId:Long) = repository.getAllFav(customerId)
    fun getCartProducts(customerId:Long)= repository.getAllCartProduct(customerId)


    /* fun getOnSaleProducts() {
         repository.getOnSaleProducts()
         onSaleProducts = repository.onSaleProductsList
     }

     fun getOnHomeProducts() {
         repository.getOnHomeProducts()
         onHomeProducts = repository.onHomeProductsList

     }*/

  //  fun getCartProducts() = repository.getAllCartProduct()


}

