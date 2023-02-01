package com.fatih.youtubeshoppinglisttestapp.ui

import android.os.AsyncTask.Status
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.adapter.ImageAdapter
import com.fatih.youtubeshoppinglisttestapp.data.remote.ImageResponse
import com.fatih.youtubeshoppinglisttestapp.getOrAwaitValue
import com.fatih.youtubeshoppinglisttestapp.launchFragmentInHiltContainer
import com.fatih.youtubeshoppinglisttestapp.other.Resource
import com.fatih.youtubeshoppinglisttestapp.repository.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ImagePickFragmentTest{

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
    fun testNavigatePopBackStackWhenImageClicked(){
        val navController=Mockito.mock(NavController::class.java)
        val testViewModel=ShoppingViewModel(FakeShoppingRepository())
        val imageUrl="Sa"
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
            this.viewModel=testViewModel
            this.imageAdapter.imageUrlList= listOf(imageUrl)
        }
        Espresso.onView(ViewMatchers.withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,ViewActions.click()
            )
        )
        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.currentImageUrl.getOrAwaitValue()).isEqualTo(imageUrl)

    }

    @Test
    fun testIfSearchStringIsNotEmptyAndShouldShowNetworkErrorFalse_returnStatusSuccess()=
        runBlocking{
        var testViewModel:ShoppingViewModel?=null
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory){
            testViewModel=this.viewModel
        }
        Espresso.onView(ViewMatchers.withId(R.id.etSearch)).perform(ViewActions.replaceText("Elm"))
        val result=testViewModel?.images?.getOrAwaitValue()?.peekContent()?.status
        assertThat(result).isEqualTo(com.fatih.youtubeshoppinglisttestapp.other.Status.LOADING)
        val result2=testViewModel?.images?.getOrAwaitValue()?.peekContent()?.status
        assertThat(result2).isEqualTo(com.fatih.youtubeshoppinglisttestapp.other.Status.SUCCESS)
    }
}