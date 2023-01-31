package com.fatih.youtubeshoppinglisttestapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem(
    private val name:String,
    private val amount:Int,
    private val price:Int,
    private val imageUrl:String,
    @PrimaryKey(autoGenerate = true)
    private val id:Int?=null
)