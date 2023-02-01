package com.fatih.youtubeshoppinglisttestapp.di

import android.content.Context
import androidx.room.Room
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("shoppingDao")
    fun provideShoppingDao(@ApplicationContext context:Context)= Room.inMemoryDatabaseBuilder(context,ShoppingDatabase::class.java)
        .allowMainThreadQueries().build().shoppingDao()


}