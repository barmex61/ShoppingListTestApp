package com.fatih.youtubeshoppinglisttestapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingItem
import com.fatih.youtubeshoppinglisttestapp.data.remote.ImageResponse
import com.fatih.youtubeshoppinglisttestapp.other.Resource

class FakeShoppingRepository :ShoppingRepositoryInterface {

    private val shoppingList= mutableListOf<ShoppingItem>()
    val shoppingListLiveData=MutableLiveData<List<ShoppingItem>>(shoppingList)
    val totalPriceLiveData=MutableLiveData<Float>()
    companion object{
        var shouldReturnNetworkError=false
    }
    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError=value
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
        refreshLiveData()
    }

    private fun getPrice(): Float {
        return shoppingList.sumByDouble {
            it.price.toDouble()
        }.toFloat()
    }

    private fun refreshLiveData(){
        shoppingListLiveData.postValue(shoppingList)
        totalPriceLiveData.postValue(getPrice())
    }

    override fun getAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingListLiveData
    }

    override fun getTotalPrice(): LiveData<Float> {
        return totalPriceLiveData
    }

    override suspend fun getImagesFromApi(query: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError){
            Resource.error(null,"Error")
        }else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}