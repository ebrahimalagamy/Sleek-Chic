package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hema.e_commerce.model.repository.Repository

class MainActivityViewModel(private val repository: Repository, app: Application) : AndroidViewModel(app) {
    fun getCartProducts(customerId:Long)= repository.getAllCartProduct(customerId)

}