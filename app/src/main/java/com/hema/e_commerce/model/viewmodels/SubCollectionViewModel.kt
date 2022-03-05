package com.hema.e_commerce.model.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections

class SubCollectionViewModel : ViewModel() {

    var productsLiveData = MutableLiveData<ArrayList<SubCollections>>()
    private val repo = Repository()

    fun getProducts(position:Int) {
        repo.getSubCollections(position)
        productsLiveData = repo.subCollectionProductsLiveData
    }
}