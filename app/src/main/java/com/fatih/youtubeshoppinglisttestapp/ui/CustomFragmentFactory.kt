package com.fatih.youtubeshoppinglisttestapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.fatih.youtubeshoppinglisttestapp.adapter.ImageAdapter
import com.fatih.youtubeshoppinglisttestapp.adapter.ShoppingItemAdapter
import javax.inject.Inject

class CustomFragmentFactory @Inject constructor(private val shoppingItemAdapter: ShoppingItemAdapter,private val imageAdapter: ImageAdapter,private val glide:RequestManager):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name->ImagePickFragment(imageAdapter=imageAdapter)
            AddShoppingItemFragment::class.java.name->AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name->ShoppingFragment(shoppingItemAdapter)
            else->super.instantiate(classLoader, className)
        }

    }
}