package com.hema.e_commerce.ui.settings.checkout


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.orderroom.OrderData


class CheckoutViewModel(private val repo: Repository, app: Application) : AndroidViewModel(app) {
     fun addOrder(orderData: OrderData) = repo.addOrder(orderData)
     //todo delete cart
     fun deleteALL(customerId:Long)=repo.deleteALL(customerId)
}