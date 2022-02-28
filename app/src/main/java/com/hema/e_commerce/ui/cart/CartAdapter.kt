package com.hema.e_commerce.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemCartBinding

class CartAdapter(
    var fragmentManager: FragmentManager?,
    val cartList: ArrayList<CartData>,
    val context: Context
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

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
        holder.itemCartBinding.textView.text = "deress"
        holder.itemCartBinding.textView2.text = "220$"
        holder.itemCartBinding.textView3.text = "free delevery"
        holder.itemCartBinding.imageView.setImageResource(R.drawable.dress)
        // holder.itemCartBinding.imageButton.setOnClickListener {  }
        // holder.itemCartBinding.imageButton4.setOnClickListener {  }
        //    holder.itemCartBinding.imageButton5.setOnClickListener {  }
        //   holder.itemCartBinding.imageButton3.setOnClickListener {  }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


}