package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct

class SingleProductViewModel(private val repo: Repository,app: Application) : AndroidViewModel(app) {

    var singleProductLiveData = MutableLiveData<ProductCollectionResponse>()

    fun getSingleProduct(productId:Long) {
        repo.getSingleProduct(productId)
        singleProductLiveData = repo.singleProductsLiveData

    }

    fun saveCartList(cartlist: CartProductData)=repo.insert(cartlist)
    fun insertFav(favoriteProduct: FavoriteProduct)=repo.insert(favoriteProduct)
    fun deleteByID(id: Long)=repo.deleteOnItemFromFavByID(id)
    fun getOneItemFromRoom(id: Long,customerId:Long) = repo.getOneItem(id,customerId)
    fun getFavProducts(customerId:Long) = repo.getAllFav(customerId)






}