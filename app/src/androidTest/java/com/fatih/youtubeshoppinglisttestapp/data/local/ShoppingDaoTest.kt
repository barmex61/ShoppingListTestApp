package com.fatih.youtubeshoppinglisttestapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.fatih.youtubeshoppinglisttestapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var database:ShoppingDatabase
    private lateinit var shoppingDao:ShoppingDao

    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),ShoppingDatabase::class.java).allowMainThreadQueries().build()
        shoppingDao=database.shoppingDao()
    }

    @Test
    fun insertShoppingItem()= runBlockingTest {
        val shoppingItem=ShoppingItem("fatih",1,1,"sas",1)
        shoppingDao.insertShoppingItem(shoppingItem)
        val allShoppingItems=shoppingDao.getAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem()= runBlockingTest {
        val shoppingItem=ShoppingItem("fatih",1,1,"sas",1)
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem)
        val allShoppingItems=shoppingDao.getAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun getTotalPriceSum()= runBlockingTest{
        val shoppingItem1=ShoppingItem("fatih",1,10,"sas",1)
        val shoppingItem2=ShoppingItem("fatih",2,20,"sas",2)
        val shoppingItem3=ShoppingItem("fatih",3,30,"sas",3)
        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)
        val result=shoppingDao.getTotalPrice().getOrAwaitValue()
        assertThat(result).isEqualTo((1*10)+(2*20)+(3*30))
    }


    @After
    fun tearDown(){
        database.close()
    }

}