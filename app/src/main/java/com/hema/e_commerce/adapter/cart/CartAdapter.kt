package com.hema.e_commerce.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemCartBinding
import com.hema.e_commerce.model.room.cartroom.CartProductData

class CartAdapter() : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.getRoot()) {
        var itemCartBinding: ItemCartBinding

        init {
            this.itemCartBinding = itemCartBinding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cart,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartProduct = differ.currentList[position]
        holder.itemCartBinding.title.text = differ.currentList[position].title
        holder.itemCartBinding.price.text = differ.currentList[position].price
        holder.itemCartBinding.copoun.text = "free delevery"
        Glide.with(holder.itemCartBinding.imageView)
            .load(differ.currentList[position].image)
            .fitCenter()
            .placeholder(R.drawable.dress)
            .into(holder.itemCartBinding.imageView)


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // util to see only change in articles to refresh it only not like we give list and we refresh all list
    private val differCallBack = object : DiffUtil.ItemCallback<CartProductData>(){
        override fun areItemsTheSame(oldItem: CartProductData,newItem:CartProductData):Boolean{
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CartProductData, newItem: CartProductData): Boolean {
            return oldItem == newItem
        }
    }

    //Async list differ take two list and compare them to change the difference only it run on background
    val differ= AsyncListDiffer(this,differCallBack)


}