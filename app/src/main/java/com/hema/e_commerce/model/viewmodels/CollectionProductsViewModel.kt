package com.hema.e_commerce.model.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.repository.Repository

class CollectionProductsViewModel : ViewModel() {

    var productsLiveData = MutableLiveData<ProductsResponse>()
    private val repo = Repository()

    fun getProducts(categoryId:Long) {
        repo.getProducts(categoryId)
        productsLiveData = repo.collectionProductsLiveData
    }
}