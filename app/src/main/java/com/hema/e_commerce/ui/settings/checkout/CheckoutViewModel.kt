package com.hema.e_commerce.ui.settings.checkout


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.room.orderroom.OrderData


class CheckoutViewModel(private val repo: Repository, app: Application) : AndroidViewModel(app) {


    suspend fun addOrder(orderData: OrderData)=repo.addOrder(orderData)


}