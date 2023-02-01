package com.fatih.youtubeshoppinglisttestapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fatih.youtubeshoppinglisttestapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePickFragment:Fragment(R.layout.fragment_image_pick) {

    private lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
    }
}