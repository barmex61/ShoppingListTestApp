package com.fatih.youtubeshoppinglisttestapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.getOrAwaitValue
import com.fatih.youtubeshoppinglisttestapp.launchFragmentInHiltContainer
import com.fatih.youtubeshoppinglisttestapp.repository.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
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
class AddShoppingItemFragmentTest{

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var hiltAndroidRule=HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }

    @Test
    fun testWhenOnPressedBackButtonNavigateUp(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.pressBack()
        Mockito.verify(navController).navigateUp()
    }

    @Test
    fun testWhenAddShoppingItemClickedNavigateImagePickFragment(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.ivShoppingImage)).perform(click())
        Mockito.verify(navController).navigate(R.id.action_addShoppingItemFragment_to_imagePickFragment)
    }

    @Test
    fun testImageUrlIsEmptyWhenOnBackPressed(){
        val viewModel=ShoppingViewModel(FakeShoppingRepository())
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
            this.viewModel=viewModel
        }
        Espresso.pressBack()
        val result=viewModel.currentImageUrl.getOrAwaitValue()
        assertThat(result).isEqualTo("")

    }
}