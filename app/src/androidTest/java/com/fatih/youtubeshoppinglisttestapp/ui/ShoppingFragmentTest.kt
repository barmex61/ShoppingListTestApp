package com.fatih.youtubeshoppinglisttestapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.adapter.ShoppingItemAdapter
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingItem
import com.fatih.youtubeshoppinglisttestapp.getOrAwaitValue
import com.fatih.youtubeshoppinglisttestapp.launchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest{


    @get:Rule
    var hiltAndroidRule=HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: CustomFragmentFactoryTest

    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }

    @Test
    fun testNavigateWhenFabButtonClicked(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.fabAddShoppingItem)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(R.id.action_shoppingFragment_to_addShoppingItemFragment)
    }

    @Test
    fun swipeShoppingItem_deleteItemIntoDb(){
        val shoppingItem=ShoppingItem("Fatih",5,5.5f,"Test",1)
        var testViewModel:ShoppingViewModel?=null
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = fragmentFactory){
            testViewModel=viewModel
            viewModel?.insertShoppingItemIntoDb(shoppingItem)
        }
        Espresso.onView(ViewMatchers.withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,ViewActions.swipeLeft()
            )
        )
        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

}