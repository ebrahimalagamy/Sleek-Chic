package com.hema.e_commerce.model.room.orderroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrderDao {
    @Insert
   suspend fun addOrder(orderData: OrderData)

    @Query("SELECT * FROM OrderData Where state= :state")
    fun getCanceledOrder(state:String): LiveData<List<OrderData>>

   @Query("UPDATE OrderData SET state = :state WHERE id = :id")
   suspend fun updateOrder(id: Int, state: String)

    @Update
    suspend fun updateState(orderData: OrderData)

    @Query("SELECT * FROM OrderData")
    fun getAllOrders(): LiveData<List<OrderData>>

    @Delete
    suspend  fun deleteOrder(orderData: OrderData)

    @Query("DELETE FROM OrderData")
    suspend fun deleteAllOrders()
}