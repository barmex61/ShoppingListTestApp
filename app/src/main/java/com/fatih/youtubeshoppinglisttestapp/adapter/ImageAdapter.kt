package com.fatih.youtubeshoppinglisttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fatih.youtubeshoppinglisttestapp.databinding.ItemImageBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(private val glide:RequestManager):
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val diffUtilCallback=object:DiffUtil.ItemCallback<String>(){
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }
    }

    private val asyncListDiffer=AsyncListDiffer(this,diffUtilCallback)

    var imageUrlList:List<String>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    inner class ImageViewHolder(val binding:ItemImageBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding=ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageUrlList.size

    }

    private var myItemLambda:((String)->Unit)?=null

    fun setMyItemLambdaListener(lambda:(String)->Unit){
        this.myItemLambda=lambda
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        glide.load(imageUrlList[position]).into(holder.binding.ivShoppingImage)
        holder.itemView.setOnClickListener {
            myItemLambda?.let {
                it(imageUrlList[position])
            }
        }
    }
}