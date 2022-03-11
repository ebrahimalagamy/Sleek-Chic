package com.hema.e_commerce.model.room.cartroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocalDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCartList(cartProduct: CartProductData)

    @Query("SELECT * FROM ProductCart")
    fun getAllCartList(): LiveData<List<CartProductData>>
    @Update
    suspend fun getCountUpdate(cartProduct: CartProductData)

    @Delete
    suspend  fun deleteOnCartItem(cartProduct: CartProductData)

    @Query("DELETE FROM ProductCart")
    suspend fun deleteAll()







}