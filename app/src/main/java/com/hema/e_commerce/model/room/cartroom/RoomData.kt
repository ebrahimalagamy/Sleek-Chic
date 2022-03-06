package com.hema.e_commerce.model.room.cartroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartProductData::class],
    version = 1,
    exportSchema = false
)
abstract class RoomData:RoomDatabase() {

    abstract fun getLocalDataObject(): LocalDataDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RoomData? = null

        fun getDatabase(context: Context): RoomData {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomData::class.java,
                    "Product_data"
                )
                .allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
    //fallbackToDestructiveMigration()
   /* companion object {
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


*/
    }


