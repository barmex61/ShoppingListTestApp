package com.fatih.youtubeshoppinglisttestapp.other

open class Event <T>(private val content:T) {

    var hasBeenHandled=false
        private set

    fun getContentIfNotHandled():T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled=true
            content
        }
    }

    fun peekContent():T=content

}