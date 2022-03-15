package com.hema.e_commerce.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.orderroom.OrderData

class OrderFragmentViewModel(private val repo: Repository, app: Application) : AndroidViewModel(app) {

    fun getActiveStateOrder(state:String,customerId:Long)=repo.getOrdersFromState(state,customerId)
    fun deleteOrder(orderData: OrderData)=repo.deleteOrder(orderData)


    fun updateOrder(orderData: OrderData)=repo.updateOrder(orderData)





}
