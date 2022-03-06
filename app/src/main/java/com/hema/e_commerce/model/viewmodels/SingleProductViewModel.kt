package com.hema.e_commerce.model.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.dataclass.singleproduct.ProductCollectionResponse

class SingleProductViewModel : ViewModel() {

    var singleProductLiveData = MutableLiveData<ProductCollectionResponse>()
    private val repo = Repository()

    fun getSingleProduct(productId:Long) {
        repo.getSingleProduct(productId)
        singleProductLiveData = repo.singleProductsLiveData

    }


}