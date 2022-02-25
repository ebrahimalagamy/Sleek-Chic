package com.hema.e_commerce.ui.category.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.ui.category.repository.Repository

class CategoryViewModel : ViewModel() {

  //  var categoriesLiveData = MutableLiveData<AllResponse>()
    private val repo = Repository();

    fun getCategories() {
//        repo.getCategories()
//        categoriesLiveData = repo.categoriesLiveData

    }


}