package com.hema.e_commerce.ui.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.orderroom.OrderData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderFragmentViewModel(private val repo: Repository, app: Application) : AndroidViewModel(app) {

    fun getActiveStateOrder(state:String)=repo.getOrdersFromState(state)


    fun updateOrder(orderData: OrderData)=repo.updateOrder(orderData)





}
