package com.fatih.youtubeshoppinglisttestapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.fatih.youtubeshoppinglisttestapp.adapter.ImageAdapter
import com.fatih.youtubeshoppinglisttestapp.adapter.ShoppingItemAdapter
import com.fatih.youtubeshoppinglisttestapp.repository.FakeShoppingRepository
import org.junit.Assert.*
import javax.inject.Inject

class CustomFragmentFactoryTest @Inject constructor(private val shoppingItemAdapter: ShoppingItemAdapter, private val imageAdapter: ImageAdapter, private val glide: RequestManager):
    FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name->ImagePickFragment(
                ShoppingViewModel(FakeShoppingRepository()),
                imageAdapter
            )
            AddShoppingItemFragment::class.java.name->AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name->ShoppingFragment(
                shoppingItemAdapter,
                ShoppingViewModel(FakeShoppingRepository())
            )
            else->super.instantiate(classLoader, className)
        }

    }
}