package com.fatih.youtubeshoppinglisttestapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingDao
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingItem
import com.fatih.youtubeshoppinglisttestapp.data.remote.ImageResponse
import com.fatih.youtubeshoppinglisttestapp.data.remote.PixabayApi
import com.fatih.youtubeshoppinglisttestapp.other.Resource
import javax.inject.Inject

class ShoppingRepository @Inject constructor(private val shoppingDao: ShoppingDao,private val pixabayApi: PixabayApi)
    :ShoppingRepositoryInterface{


    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        try {
            shoppingDao.insertShoppingItem(shoppingItem)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        try {
            shoppingDao.deleteShoppingItem(shoppingItem)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getAllShoppingItem(): LiveData<List<ShoppingItem>> {
       return try {
            shoppingDao.getAllShoppingItem()
        }catch (e:Exception){
            MutableLiveData()
        }
    }

    override fun getTotalPrice(): LiveData<Float> {
        return try {
            shoppingDao.getTotalPrice()
        }catch (e:Exception){
            MutableLiveData()
        }
    }

    override suspend fun getImagesFromApi(query: String): Resource<ImageResponse> {
        return try {
            val result=pixabayApi.getImagesFromApi(query = query)
            if(result.isSuccessful){
                result.body()?.let {
                    Resource.success(it)
                }?: Resource.error(null,"Body Null")
            }else{
                Resource.error(null,"Response failed")
            }
        }catch (e:Exception){
            Resource.error(null,e.message)
        }
    }
}