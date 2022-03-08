package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.cartroom.LocalDataDao
import com.hema.e_commerce.model.room.cartroom.LocalDataDao_Impl
import com.hema.e_commerce.model.room.cartroom.RoomData
import kotlinx.coroutines.launch

class SingleProductViewModel(private val repo: Repository,app: Application) : AndroidViewModel(app) {

    var singleProductLiveData = MutableLiveData<ProductCollectionResponse>()
/*    private val repo = Repository()*/

    fun getSingleProduct(productId:Long) {
        repo.getSingleProduct(productId)
        singleProductLiveData = repo.singleProductsLiveData

    }

    fun saveCartList(cartlist: CartProductData)=repo.insert(cartlist)



    }