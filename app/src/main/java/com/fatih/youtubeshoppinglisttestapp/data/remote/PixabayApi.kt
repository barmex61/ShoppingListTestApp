package com.fatih.youtubeshoppinglisttestapp.data.remote

import com.fatih.youtubeshoppinglisttestapp.BuildConfig.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    //pixabay.com/api/?key=26104390-18ce4e1309f8c4869a8da43a5&q=yellow+flower

    @GET("/api/")
    suspend fun getImagesFromApi(
        @Query("key") key :String =API_KEY,
        @Query("q") query:String
    ):Response<ImageResponse>
}