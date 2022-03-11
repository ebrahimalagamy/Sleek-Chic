package com.hema.e_commerce.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.cartroom.LocalDataDao
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteDao
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.room.orderroom.OrderDao
import com.hema.e_commerce.model.room.orderroom.OrderData

@Database(entities = [CartProductData::class,OrderData::class,FavoriteProduct::class],
    version = 1,
    exportSchema = false
)
abstract class RoomData:RoomDatabase() {

    abstract fun getLocalDataObject(): LocalDataDao
    abstract fun getOrder(): OrderDao
    abstract fun getFavoriteData(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: RoomData? = null
        private val LOCk=Any()
        //called when take object from the database
        operator fun invoke(context: Context)= INSTANCE ?: synchronized(LOCk){
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }


        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                RoomData::class.java,
                "Product_db.db"
            ).build()
    }
    


    }


