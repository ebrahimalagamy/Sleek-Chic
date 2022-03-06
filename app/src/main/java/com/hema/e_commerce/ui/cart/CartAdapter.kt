package com.hema.e_commerce.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemCartBinding
import com.hema.e_commerce.model.room.cartroom.CartProductData

class CartAdapter(
    val cartList: ArrayList<CartProductData>,
    val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private val lastPosition=-1


    fun addNewList(orderNewList: List<CartProductData>) {
        cartList.clear()
        cartList.addAll(orderNewList)
        notifyDataSetChanged()

    }

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
        val cartProduct = cartList[position]
        holder.itemCartBinding.textView.text = cartList[position].title
        holder.itemCartBinding.textView2.text = cartList[position].price
        holder.itemCartBinding.textView3.text = "free delevery"
        Glide.with(holder.itemCartBinding.imageView)
            .load(cartList[position].image)
            .fitCenter()
            .placeholder(R.drawable.dress)
            .into(holder.itemCartBinding.imageView)


        holder.itemCartBinding.minCount.setOnClickListener {
            var num =((holder.itemCartBinding.count.text.toString().toInt())-1)
            if(num>0){
                cartList[position].price
                holder.itemCartBinding.count.text=num.toString()
                cartViewModel.onChangeQuntity()
            }
            else{
                cartViewModel.onDelClick( cartList[position].id)
            }



        }
        holder.itemCartBinding.increCount.setOnClickListener {


            var num =((holder.itemCartBinding.count.text.toString().toInt())+1)
            cartList[position].price
            holder.itemCartBinding.count.text=num.toString()
            cartViewModel.onChangeQuntity()

        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


}