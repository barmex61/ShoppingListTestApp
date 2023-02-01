package com.fatih.youtubeshoppinglisttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fatih.youtubeshoppinglisttestapp.data.local.ShoppingItem
import com.fatih.youtubeshoppinglisttestapp.databinding.ItemImageBinding
import com.fatih.youtubeshoppinglisttestapp.databinding.ItemShoppingBinding
import javax.inject.Inject

class ShoppingItemAdapter @Inject constructor(private val glide:RequestManager):
    RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {

    private val diffUtilCallback=object:DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem==newItem
        }

        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id==newItem.id
        }
    }

    private val asyncListDiffer=AsyncListDiffer(this,diffUtilCallback)

    var shoppingItemList:List<ShoppingItem>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    inner class ShoppingItemViewHolder(val binding:ItemShoppingBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding=ItemShoppingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShoppingItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return shoppingItemList.size

    }

    private var myItemLambda:((ShoppingItem)->Unit)?=null

    fun setMyItemLambdaListener(lambda:(ShoppingItem)->Unit){
        this.myItemLambda=lambda
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        glide.load(shoppingItemList[position].imageUrl).into(holder.binding.ivShoppingImage)
        holder.itemView.setOnClickListener {
            holder.binding.tvShoppingItemPrice.text=shoppingItemList[position].price.toString()
            holder.binding.tvShoppingItemAmount.text=shoppingItemList[position].amount.toString()
            holder.binding.tvName.text=shoppingItemList[position].name.toString()
            myItemLambda?.let {
                it(shoppingItemList[position])
            }
        }
    }
}