package com.hema.e_commerce.model.room.cartroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocalDataDao {
    // query of  cart table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCartList(cartProduct: CartProductData)

    @Query("SELECT * FROM ProductCart")
    fun getAllCartList(): LiveData<List<CartProductData>>

/*
    @Query("SELECT * FROM OrderTable")
    fun getAllOrderList(): LiveData<List<OneOrderdata>>
*/

   /* @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCartList(withItem: CartProductData?)*/

 /*   @Query("DELETE FROM productcart WHERE id = :id")
    suspend fun deleteOneCartItem(id: Long)*/

  @Delete
  fun deleteOnCartItem(cartProduct: CartProductData)

    @Query("DELETE FROM ProductCart")

    fun deleteAllFromCart()






}