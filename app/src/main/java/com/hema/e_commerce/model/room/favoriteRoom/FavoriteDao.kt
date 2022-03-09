package com.hema.e_commerce.model.room.favoriteRoom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteProduct: FavoriteProduct)

    @Delete
    suspend fun delete(favoriteProduct: FavoriteProduct)

    @Query("DELETE FROM FavoriteProduct")
    suspend fun deleteAll()

    @Query("DELETE FROM FavoriteProduct Where id= :id")
    suspend fun deleteById(id:Long)

    @Query ("SELECT * FROM FavoriteProduct")
    fun getAllFav():LiveData<List<FavoriteProduct>>

    @Query ("SELECT * FROM FavoriteProduct Where id= :id")
    fun getOneItem(id:Long):LiveData<FavoriteProduct>
}