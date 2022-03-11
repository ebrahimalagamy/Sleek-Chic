package com.hema.e_commerce.ui.order

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hema.e_commerce.model.repository.Repository

class CancelledOrderFragmentViewModelFactory (
    val app: Application,
    val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CancelledOrderFragmentViewModel(repository, app) as T
    }
}
