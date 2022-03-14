package com.hema.e_commerce

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hema.e_commerce.model.repository.Repository

class MainActivityViewModel(private val repository: Repository, app: Application) : AndroidViewModel(app) {
    fun getCartProducts()= repository.getAllCartProduct()

}