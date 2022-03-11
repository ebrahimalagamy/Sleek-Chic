package com.hema.e_commerce.ui.order

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hema.e_commerce.model.repository.Repository

class OrderFragmentViewModelFactory (
    val app: Application,
    val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderFragmentViewModel(repository, app) as T
    }
}
