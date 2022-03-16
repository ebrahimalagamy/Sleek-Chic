package com.hema.e_commerce.model.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hema.e_commerce.model.remote.currancynetwork.CurrancyRepository
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.viewmodels.CategoryViewModel
import com.hema.e_commerce.model.viewmodels.CurrancyViewModel

class CurrancyViewModelFactory( val app: Application,
val repository: CurrancyRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrancyViewModel(repository,app) as T
    }
}


