package com.fatih.youtubeshoppinglisttestapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatih.youtubeshoppinglisttestapp.R
import com.fatih.youtubeshoppinglisttestapp.adapter.ImageAdapter
import com.fatih.youtubeshoppinglisttestapp.databinding.FragmentImagePickBinding
import com.fatih.youtubeshoppinglisttestapp.other.Constants.GRID_SPAN_COUNT
import com.fatih.youtubeshoppinglisttestapp.other.Constants.SEARCH_TIME_DELAY
import com.fatih.youtubeshoppinglisttestapp.other.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickFragment @Inject constructor(var viewModel: ShoppingViewModel?=null, val imageAdapter: ImageAdapter):Fragment(R.layout.fragment_image_pick) {

    private lateinit var binding:FragmentImagePickBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentImagePickBinding.bind(view)
        viewModel=viewModel?: ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        setupRecyclerView()
        subscribeToObservers()

        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                it?.let {
                    try {
                        val query=it.toString()
                        if(query.isNotEmpty())
                        viewModel?.searchForImage(query)

                    }catch (e:Exception){

                    }
                }
            }
        }

        imageAdapter.setMyItemLambdaListener {
            findNavController().popBackStack()
            viewModel?.setCurrentImageUrl(it)
        }
    }

    private fun subscribeToObservers() {
        viewModel?.images?.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        val urls = result.data?.imageResults?.map { imageResult ->  imageResult.previewURL }
                        imageAdapter.imageUrlList = urls ?: listOf()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireView(),
                            result.message ?: "An unknown error occured.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupRecyclerView(){
        binding.rvImages.apply {
            layoutManager=GridLayoutManager(requireContext(),GRID_SPAN_COUNT)
            adapter=imageAdapter
        }
    }
}