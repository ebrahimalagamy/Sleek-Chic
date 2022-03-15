package com.hema.e_commerce.model.room.favoriteRoom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteProduct: FavoriteProduct)

    @Delete
    suspend fun delete(favoriteProduct: FavoriteProduct)

   /* @Query("DELETE FROM FavoriteProduct")
    suspend fun deleteAll()
*/
    @Query("DELETE FROM FavoriteProduct Where id= :id")
    suspend fun deleteById(id:Long)

    @Query ("SELECT * FROM FavoriteProduct Where customer_id= :customerId")
    fun getAllFav(customerId:Long):LiveData<List<FavoriteProduct>>

    @Query ("SELECT * FROM FavoriteProduct Where id= :id and customer_id= :customerId")
    fun getOneItem(id:Long,customerId:Long):LiveData<FavoriteProduct>
}