package com.hema.e_commerce.ui.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
val repository:Repository= Repository()

    fun getBrand()=repository.getBrand()
    fun getBrandLiveData()=repository.getBrandLiveData()


}