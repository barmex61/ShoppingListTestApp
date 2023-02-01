package com.fatih.youtubeshoppinglisttestapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest{


    @get:Rule
    var hiltAndroidRule=HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }

    @Test
    fun testNavigateWhenFabButtonClicked(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.fabAddShoppingItem)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(R.id.action_shoppingFragment_to_addShoppingItemFragment)
    }

}