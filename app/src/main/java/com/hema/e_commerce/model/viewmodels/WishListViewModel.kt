package com.hema.e_commerce.model.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct


class WishListViewModel(private val repo: Repository, app: Application) : AndroidViewModel(app) {

    fun getFavProducts(customerId:Long)=repo.getAllFav(customerId)
    fun saveCartList(cartlist: CartProductData)=repo.insert(cartlist)
    fun insertFav(favoriteProduct: FavoriteProduct)=repo.insert(favoriteProduct)
    fun delete(favorite: FavoriteProduct)=repo.deleteOnItemFromFav(favorite)


}