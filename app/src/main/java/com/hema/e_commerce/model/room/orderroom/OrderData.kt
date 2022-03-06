package com.hema.e_commerce.model.room.orderroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "OrderTable")
data class OrderData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")

    val id:Long,
    @ColumnInfo(name="title")

    val title: String,
    @ColumnInfo(name="price")

    val price: String,
    @ColumnInfo(name="image")

    val image: String,
/*
    @ColumnInfo(name="item_number")

    val item_number: String
*/


        )
