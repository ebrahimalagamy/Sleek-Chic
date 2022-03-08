package com.hema.e_commerce.model.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData


class CartViewModel(private val repo: Repository,app: Application) : AndroidViewModel(app) {
    var cartProductLiveData = MutableLiveData<CartProductData>()
    private val ChangeQuntityListener = MutableLiveData<Boolean>()
    private val delOrder = MutableLiveData<Long>()


  /*  fun getOrderQuntity(): LiveData<Boolean> {
        return ChangeQuntityListener
    }*/

 /*   fun getAllCartList(){

   }*/
    fun getPostOrder()= repo.getcreatAnOrder()
/*    fun onChangeQuntity() {
        ChangeQuntityListener.postValue(true)
    }
    fun onDelClick(id: Long) {
        delOrder.postValue(id)
    }*/


fun getCartProducts()=repo.getAllCartProduct()

}