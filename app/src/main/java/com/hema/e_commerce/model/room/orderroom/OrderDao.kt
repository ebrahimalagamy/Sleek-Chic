package com.hema.e_commerce.model.room.orderroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrderDao {
    @Insert
   suspend fun addOrder(orderData: OrderData)

    @Query("SELECT * FROM OrderData Where state= :state")
    fun getCanceledOrder(state:String): LiveData<List<OrderData>>

   @Query("UPDATE OrderData SET state = :state WHERE orderNumber = :orderNumber")
   suspend fun updateOrder(orderNumber: Long, state: String)

    @Update
    suspend fun updateState(orderData: OrderData)

    @Query("SELECT * FROM OrderData")
    fun getAllOrders(): LiveData<List<OrderData>>

    @Query("SELECT * FROM OrderData Where State= :state")
    fun getOrdersFromState(state: String): LiveData<List<OrderData>>

    @Delete
    suspend  fun deleteOrder(orderData: OrderData)

    @Query("DELETE FROM OrderData")
    suspend fun deleteAllOrders()
}