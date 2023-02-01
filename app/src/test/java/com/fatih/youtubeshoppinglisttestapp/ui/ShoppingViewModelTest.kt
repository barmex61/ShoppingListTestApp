package com.fatih.youtubeshoppinglisttestapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.kotlincoroutines.main.utils.MainCoroutineRule
import com.fatih.youtubeshoppinglisttestapp.getOrAwaitValue
import com.fatih.youtubeshoppinglisttestapp.other.Constants
import com.fatih.youtubeshoppinglisttestapp.other.Status
import com.fatih.youtubeshoppinglisttestapp.repository.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()

    private lateinit var viewModel:ShoppingViewModel

    @Before
    fun setUp() {
        viewModel=ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field returns error`(){
        viewModel.insertShoppingItem("","amoun","yes")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name returns error`(){
        val string= buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH+1){
                this.append(1)
            }
        }
        viewModel.insertShoppingItem(string,"25","2.5")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price returns error`(){
        val string= buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH+1){
                this.append(1)
            }
        }
        viewModel.insertShoppingItem("Elma","10",string)
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long amount returns error`(){

        viewModel.insertShoppingItem("Elma","99999999999999999","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping valid return success`(){

        viewModel.insertShoppingItem("Elma","10","2.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @After
    fun tearDown() {
    }
}