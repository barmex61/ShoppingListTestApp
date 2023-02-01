package com.fatih.youtubeshoppinglisttestapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem(
    val name:String,
    val amount:Int,
    val price:Float,
    val imageUrl:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null
)