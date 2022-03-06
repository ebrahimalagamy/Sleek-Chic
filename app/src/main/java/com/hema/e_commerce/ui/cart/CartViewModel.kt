package com.hema.e_commerce.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.cartroom.LocalDataDao

class CartViewModel(application: Application) : AndroidViewModel(application) {
    var cartProductLiveData = MutableLiveData<CartProductData>()
    private val ChangeQuntityListener = MutableLiveData<Boolean>()
    private val delOrder = MutableLiveData<Long>()


    private val repo = Repository()

    fun getOrderQuntity(): LiveData<Boolean> {
        return ChangeQuntityListener
    }
    fun insertAllOrder(dataList: List<CartProductData>)=repo.saveCartList()
    fun getAllCartList() = repo.getAllCartProduct()
    fun getPostOrder()= repo.getcreatAnOrder()
    fun onChangeQuntity() {
        ChangeQuntityListener.postValue(true)
    }
    fun onDelClick(id: Long) {
        delOrder.postValue(id)
    }




}