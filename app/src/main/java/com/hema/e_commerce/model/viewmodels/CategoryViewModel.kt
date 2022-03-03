package com.hema.e_commerce.model.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.repository.Repository

class CategoryViewModel : ViewModel() {

    var categoriesLiveData = MutableLiveData<CustomCollectionsResponse>()
    private val repo = Repository();

    fun getCollections() {
        repo.getCollections()
        categoriesLiveData = repo.collectionsLiveData

    }


}