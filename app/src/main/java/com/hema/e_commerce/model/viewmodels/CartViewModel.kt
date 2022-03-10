package com.hema.e_commerce.model.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct


class CartViewModel(private val repo: Repository,app: Application) : AndroidViewModel(app) {

    fun getCartProducts()= repo.getAllCartProduct()

    fun deleteOneItemCart(cartItem: CartProductData)=repo.deleteOneCartItem(cartItem)

    fun updateItemCount(cartItem: CartProductData)=repo.updateCountChange(cartItem)
    fun insertFav(favoriteProduct: FavoriteProduct)=repo.insert(favoriteProduct)



}