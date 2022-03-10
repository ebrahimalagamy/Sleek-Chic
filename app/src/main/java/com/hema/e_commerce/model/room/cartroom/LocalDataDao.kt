package com.hema.e_commerce.model.room.cartroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocalDataDao {
    // query of  cart table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCartList(cartProduct: CartProductData)

    @Query("SELECT * FROM ProductCart")
    fun getAllCartList(): LiveData<List<CartProductData>>

    @Delete
    suspend  fun deleteOnCartItem(cartProduct: CartProductData)

    @Query("DELETE FROM ProductCart")
    suspend fun deleteAll()

/*
    @Query("SELECT * FROM OrderTable")
    fun getAllOrderList(): LiveData<List<OneOrderdata>>
*/

   /* @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCartList(withItem: CartProductData?)*/

 /*   @Query("DELETE FROM productcart WHERE id = :id")
    suspend fun deleteOneCartItem(id: Long)*/

     /* @Delete
      fun deleteAllFromCart()*/



/*    @Query("DELETE FROM ProductCart")
      fun deleteOnCartItem(cartProduct: CartProductData)*/








}