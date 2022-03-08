package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollectionsResponse
import com.hema.e_commerce.model.repository.Repository

class CategoryViewModel(private val repo: Repository,app: Application) : AndroidViewModel(app) {

    var categoriesLiveData = MutableLiveData<CustomCollectionsResponse>()

    fun getCollections() {
        repo.getCollections()
        categoriesLiveData = repo.collectionsLiveData

    }


}