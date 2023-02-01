package com.fatih.youtubeshoppinglisttestapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.databinding.FragmentAddShoppingItemBinding
import com.fatih.youtubeshoppinglisttestapp.other.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingItemFragment @Inject constructor(private val glide:RequestManager):Fragment(R.layout.fragment_add_shopping_item) {

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
        binding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                binding.etShoppingItemName.text.toString(),
                binding.etShoppingItemAmount.text.toString(),
                binding.etShoppingItemPrice.text.toString()
            )
        }
        subscribeToObservers()
    }

    private fun subscribeToObservers(){
        viewModel.currentImageUrl.observe(viewLifecycleOwner){
            glide.load(it).into(binding.ivShoppingImage)
        }
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                when(it.status){
                    Status.SUCCESS->{
                        Snackbar.make(requireView(),"Added shopping item",Snackbar.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                    Status.ERROR->{
                        Snackbar.make(requireView(),"Error occurred",Snackbar.LENGTH_SHORT).show()
                    }
                    Status.LOADING->{}
                }
            }
        }
    }

}