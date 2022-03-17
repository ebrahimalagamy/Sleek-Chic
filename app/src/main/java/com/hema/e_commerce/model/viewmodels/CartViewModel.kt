package com.hema.e_commerce.model.viewmodels


import android.app.Application
import androidx.lifecycle.*
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import kotlinx.coroutines.launch


class CartViewModel(private val repo: Repository,app: Application) : AndroidViewModel(app) {
    private val count = MutableLiveData<Int>()
    private val ChangeQuntityListener = MutableLiveData<Boolean>()
    private val order = MutableLiveData<Long>()
    private val favOrder = MutableLiveData<CartProductData>()
    fun deleteOnItemCart(cartItem: CartProductData)=repo.deleteOneCartItem(cartItem)
    var deleteItem : MutableLiveData<CartProductData> = MutableLiveData()
    fun getFavProducts(customerId:Long) = repo.getAllFav(customerId)



    fun onCountChange(item:Int){
        count.postValue(item)
    }




    fun onChangeQuntity() {
        ChangeQuntityListener.postValue(true)
    }
    fun onDelClick(id: Long) {
        order.postValue(id)
    }

    fun onFavClick(item: CartProductData) {
        favOrder.postValue(item)
    }



    fun getCartProducts(customerId:Long)= repo.getAllCartProduct(customerId)
  //  fun getCartProductCount(id:Long)= repo.getCartproduct(id)



    fun deleteOneItemCart(cartItem: CartProductData)=repo.deleteOneCartItem(cartItem)

    fun updateItemCount(cartItem: CartProductData)=repo.updateCountChange(cartItem)
    fun insertFav(favoriteProduct: FavoriteProduct)=repo.insert(favoriteProduct)



}