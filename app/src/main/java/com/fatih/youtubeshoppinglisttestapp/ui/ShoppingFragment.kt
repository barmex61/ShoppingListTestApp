package com.fatih.youtubeshoppinglisttestapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.databinding.FragmentShoppingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment:Fragment(R.layout.fragment_shopping) {

    private lateinit var viewModel: ShoppingViewModel
    private lateinit var binding:FragmentShoppingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentShoppingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingFragment_to_addShoppingItemFragment)
        }
    }

}