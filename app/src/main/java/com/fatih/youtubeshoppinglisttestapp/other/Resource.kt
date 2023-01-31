package com.fatih.youtubeshoppinglisttestapp.other

class Resource <T>(val data:T?,val message:String?,val status:Status) {

    companion object{
        fun <T> success(data:T):Resource<T>{
            return Resource(data,null,Status.SUCCESS)
        }
        fun <T> loading(data:T?):Resource<T>{
            return Resource(data,null,Status.LOADING)
        }
        fun <T> error(data:T?,message: String?):Resource<T>{
            return Resource(data,message,Status.ERROR)
        }
    }
}

enum class Status{
    LOADING,SUCCESS,ERROR
}