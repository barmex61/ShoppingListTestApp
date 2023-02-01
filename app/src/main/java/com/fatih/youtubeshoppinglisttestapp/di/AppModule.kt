package com.fatih.youtubeshoppinglisttestapp.di

import android.content.Context
import androidx.room.Room
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingDao
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingDatabase
import com.fatih.youtubeshoppinglisttestapp.data.remote.PixabayApi
import com.fatih.youtubeshoppinglisttestapp.other.Constants.BASE_URL
import com.fatih.youtubeshoppinglisttestapp.other.Constants.DATABASE_NAME
import com.fatih.youtubeshoppinglisttestapp.repository.ShoppingRepository
import com.fatih.youtubeshoppinglisttestapp.repository.ShoppingRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingDao(@ApplicationContext context: Context)=
        Room.databaseBuilder(context,ShoppingDatabase::class.java,DATABASE_NAME).build()
            .shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi()=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .build().create(PixabayApi::class.java)

    @Provides
    @Singleton
    fun provideShoppingRepository(shoppingDao: ShoppingDao,api:PixabayApi)=ShoppingRepository(shoppingDao,api) as ShoppingRepositoryInterface

}