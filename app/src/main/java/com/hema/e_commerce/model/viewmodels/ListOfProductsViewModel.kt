package com.hema.e_commerce.model.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.ui.category.testmodels.SubCollections

class ListOfProductsViewModel : ViewModel() {

    var SubCollectionsProductsLiveData = MutableLiveData<ProductsResponse>()
    private val repo = Repository()

    fun getSubCollectionsProducts(id:Long) {
        repo.getSubCollectionsProducts(id)
        SubCollectionsProductsLiveData = repo.collectionProductsLiveData
    }
}