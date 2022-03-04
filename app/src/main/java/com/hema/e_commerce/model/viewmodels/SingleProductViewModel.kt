package com.hema.e_commerce.model.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.repository.Repository

class SingleProductViewModel : ViewModel() {

    var singleProductLiveData = MutableLiveData<Product>()
    private val repo = Repository();

    fun getSingleProduct(productId:Long) {
        repo.getSingleProduct(productId)
        singleProductLiveData = repo.singleProductsLiveData

    }
}