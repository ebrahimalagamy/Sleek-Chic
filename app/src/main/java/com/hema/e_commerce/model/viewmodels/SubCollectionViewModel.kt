package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.repository.SubCollectionsRepo
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections

class SubCollectionViewModel(private val repo: SubCollectionsRepo, app: Application) : AndroidViewModel(app) {

    var productsLiveData = MutableLiveData<ArrayList<SubCollections>>()
/*
    private val repo = Repository()
*/

    fun getProducts(position:Int) {
        repo.getSubCollections(position)
        productsLiveData = repo.subCollectionProductsLiveData
    }
}