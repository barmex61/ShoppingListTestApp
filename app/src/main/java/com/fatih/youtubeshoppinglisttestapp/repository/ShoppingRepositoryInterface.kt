package com.fatih.youtubeshoppinglisttestapp.repository

import androidx.lifecycle.LiveData
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingItem
import com.fatih.youtubeshoppinglisttestapp.data.remote.ImageResponse
import com.fatih.youtubeshoppinglisttestapp.other.Resource

interface ShoppingRepositoryInterface {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun getAllShoppingItem():LiveData<List<ShoppingItem>>
    fun getTotalPrice():LiveData<Float>
    suspend fun getImagesFromApi(query:String):Resource<ImageResponse>

}