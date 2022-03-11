package com.hema.e_commerce.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemOrderBinding
import com.hema.e_commerce.model.room.orderroom.OrderData

class CancelledOrderAdapter (var viewModel: OrderFragmentViewModel) : RecyclerView.Adapter<CancelledOrderAdapter.ViewHolder>() {

    inner class ViewHolder( val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancelledOrderAdapter.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_order, parent, false
        )
        )
    }

    override fun getItemCount(): Int {
        return   differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvOrderNumber.text=differ.currentList[position].orderNumber.toString()
        holder.binding.tvState.text=differ.currentList[position].state

        holder.binding.tvAddress.text=differ.currentList[position].address
        holder.binding.tvName.text=differ.currentList[position].customerName
        holder.binding.tvPhone.text=differ.currentList[position].phone

        holder.binding.tvPayment.text=differ.currentList[position].payMethod
        holder.binding.tvPrice.text=differ.currentList[position].totalPrice
        holder.binding.btnCancelOrder.visibility= View.GONE
    }


    // util to see only change in articles to refresh it only not like we give list and we refresh all list
    private val differCallBack = object : DiffUtil.ItemCallback<OrderData>(){
        override fun areItemsTheSame(oldItem: OrderData, newItem: OrderData):Boolean{
            return oldItem.orderNumber == newItem.orderNumber
        }
        override fun areContentsTheSame(oldItem: OrderData, newItem: OrderData): Boolean {
            return oldItem == newItem
        }
    }

    //Async list differ take two list and compare them to change the difference only it run on background
    val differ= AsyncListDiffer(this,differCallBack)
}