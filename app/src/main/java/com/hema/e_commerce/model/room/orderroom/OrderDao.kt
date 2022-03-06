package com.hema.e_commerce.model.room.orderroom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert
    fun addOrder(orderData: OrderData)
    @Query("SELECT * FROM OrderTable")
    fun getAllOrderList(): LiveData<List<OrderData>>
}