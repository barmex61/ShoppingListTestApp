package com.fatih.youtubeshoppinglisttestapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM ShoppingItem")
    fun getAllShoppingItem():LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price*amount) FROM ShoppingItem")
    fun getTotalPrice():LiveData<Float>
}