package com.hema.e_commerce.model.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.viewmodels.SingleProductViewModel

class SingleProductViewModelFactory(
    val app: Application,
    val repository:Repository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleProductViewModel(repository,app) as T
    }
}