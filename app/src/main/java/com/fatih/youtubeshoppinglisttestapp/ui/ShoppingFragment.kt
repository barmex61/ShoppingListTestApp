package com.fatih.youtubeshoppinglisttestapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.adapter.ShoppingItemAdapter
import com.fatih.youtubeshoppinglisttestapp.databinding.FragmentShoppingBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingFragment @Inject constructor(
    private val shoppingItemAdapter: ShoppingItemAdapter,
    var viewModel: ShoppingViewModel?=null):Fragment(R.layout.fragment_shopping) {

    private lateinit var binding:FragmentShoppingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentShoppingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=viewModel?:ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        setupRecyclerView()
        subscribeToObservers()
        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingFragment_to_addShoppingItemFragment)
        }
    }

    val callback=object :ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean=true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos=viewHolder.layoutPosition
            val shoppingItem=shoppingItemAdapter.shoppingItemList[pos]
            viewModel?.deleteShoppingItem(shoppingItem)
            Snackbar.make(requireView(),"Successfully deleted item",Snackbar.LENGTH_SHORT).apply {
                setAction("Undo"){view->
                    viewModel?.insertShoppingItemIntoDb(shoppingItem)
                }
            }.show()
        }
    }

    private fun subscribeToObservers(){
        viewModel?.shoppingItems?.observe(viewLifecycleOwner){
            shoppingItemAdapter.shoppingItemList=it
        }
        viewModel?.totalPrice?.observe(viewLifecycleOwner){
            val price=it?:0f
            val priceText="Total price: $price TL"
            binding.tvShoppingItemPrice.text=priceText
        }
    }
    private fun setupRecyclerView(){

        binding.rvShoppingItems.apply{
            adapter=shoppingItemAdapter
            layoutManager=LinearLayoutManager(requireContext())
            ItemTouchHelper(callback).attachToRecyclerView(this)
        }
    }

}