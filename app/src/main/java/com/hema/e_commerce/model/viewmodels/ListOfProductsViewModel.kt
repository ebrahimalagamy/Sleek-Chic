package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.repository.Repository

class ListOfProductsViewModel(private val repo: Repository,app: Application) : AndroidViewModel(app) {

    var SubCollectionsProductsLiveData = MutableLiveData<ProductsResponse>()
/*
    private val repo = Repository()
*/

    fun getSubCollectionsProducts(id:Long) {
        repo.getSubCollectionsProducts(id)
        SubCollectionsProductsLiveData = repo.collectionProductsLiveData
    }
    var allProduct = MutableLiveData<ProductsResponse>()
    fun getProducts(){
        repo.getallProduct()
        allProduct=repo.allProduct
    }


}