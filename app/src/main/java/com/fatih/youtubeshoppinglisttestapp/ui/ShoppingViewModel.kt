package com.fatih.youtubeshoppinglisttestapp.ui

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingItem
import com.fatih.youtubeshoppinglisttestapp.data.remote.ImageResponse
import com.fatih.youtubeshoppinglisttestapp.other.Constants
import com.fatih.youtubeshoppinglisttestapp.other.Event
import com.fatih.youtubeshoppinglisttestapp.other.Resource
import com.fatih.youtubeshoppinglisttestapp.repository.ShoppingRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(private val repository: ShoppingRepositoryInterface) :ViewModel() {

    val shoppingItems=repository.getAllShoppingItem()
    val totalPrice=repository.getTotalPrice()

    private val _images=MutableLiveData<Event<Resource<ImageResponse>>>()
    val images:LiveData<Event<Resource<ImageResponse>>>
        get() = _images

    private val _currentImageUrl=MutableLiveData<String>()
    val currentImageUrl:LiveData<String>
        get() = _currentImageUrl

    private val _insertShoppingItemStatus=MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus:LiveData<Event<Resource<ShoppingItem>>>
        get() = _insertShoppingItemStatus

    fun setCurrentImageUrl(url:String){
        _currentImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem)=viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem)=viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name:String,amountString:String,price:String){
        _insertShoppingItemStatus.postValue(Event(Resource.loading(null)))
        if(name.isEmpty()||amountString.isEmpty()||price.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error(null,"All fields must be filled")))
            return
        }
        if(name.length>Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error(null,"The name of item" +
                    "must not exceed ${Constants.MAX_NAME_LENGTH} characters")))
            return
        }
        if(price.length>Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error(null,"The price of item" +
                    "must not exceed ${Constants.MAX_PRICE_LENGTH} characters")))
            return
        }
        val amount=try {
            amountString.toInt()
        }catch (e:Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error(null,"Please enter valid amount")))
            return
        }
        val shoppingItem=ShoppingItem(name,amount,price.toFloat(),currentImageUrl.value?:"")
        insertShoppingItemIntoDb(shoppingItem)
        setCurrentImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(query: String){

        if(query.isEmpty()){
            return
        }
        _images.value= Event(Resource.loading(null))
        viewModelScope.launch {
            val response=repository.getImagesFromApi(query)
            _images.value=Event(response)
        }
    }

}