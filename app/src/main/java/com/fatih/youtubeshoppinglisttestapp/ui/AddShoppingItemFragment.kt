package com.fatih.youtubeshoppinglisttestapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.databinding.FragmentAddShoppingItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddShoppingItemFragment:Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel
    private lateinit var binding:FragmentAddShoppingItemBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentAddShoppingItemBinding.bind(view)
        viewModel= ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        binding.ivShoppingImage.setOnClickListener{
            findNavController().navigate(R.id.action_addShoppingItemFragment_to_imagePickFragment)
        }

        val callback=object:OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setCurrentImageUrl("")
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}