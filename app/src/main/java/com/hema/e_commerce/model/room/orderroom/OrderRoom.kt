package com.hema.e_commerce.model.room.orderroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hema.e_commerce.model.room.cartroom.CartProductData

@Database(entities = [OrderData::class],
    version = 1,
    exportSchema = false
)

abstract class OrderRoom :RoomDatabase(){
    abstract fun getOrderDataObject(): OrderDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: OrderRoom? = null

        fun getOrderDatabase(context: Context): OrderRoom {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderRoom::class.java,
                    "order_table"
                )
                    .allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}