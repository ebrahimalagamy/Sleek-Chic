package com.hema.e_commerce.model.room.cartroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocalDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCartList(cartProduct: CartProductData)

    @Query("SELECT * FROM ProductCart Where customer_id= :customerId")
    fun getAllCartList(customerId:Long): LiveData<List<CartProductData>>

    /*@Query("SELECT count FROM ProductCart Where id= :id")
    fun getCartproduct(id:Long):Int*/

    @Update
    suspend fun getCountUpdate(cartProduct: CartProductData)

    @Delete
    suspend  fun deleteOnCartItem(cartProduct: CartProductData)

    @Query("DELETE FROM ProductCart Where customer_id= :customerId")
    suspend fun deleteAll(customerId:Long)







}