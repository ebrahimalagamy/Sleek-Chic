package com.hema.e_commerce.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemOrderBinding
import com.hema.e_commerce.model.room.orderroom.OrderData

class OrderAdapter (val viewModel: OrderFragmentViewModel,val context: Context) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    lateinit var order:OrderData

    inner class ViewHolder( val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_order, parent, false)
        )
    }

    override fun getItemCount(): Int {
     return   differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (differ.currentList.size>-1) {
             order = OrderData(
                differ.currentList[position].orderNumber,
                differ.currentList[position].totalPrice,
                differ.currentList[position].customerName,
                differ.currentList[position].address,
                differ.currentList[position].phone,
                differ.currentList[position].payMethod,
                "CANCELLED"
            )
        }
        holder.binding.tvOrderNumber.text=differ.currentList[position].orderNumber.toString()
        holder.binding.tvState.text=differ.currentList[position].state

        holder.binding.tvAddress.text=differ.currentList[position].address
        holder.binding.tvName.text=differ.currentList[position].customerName
        holder.binding.tvPhone.text=differ.currentList[position].phone

        holder.binding.tvPayment.text=differ.currentList[position].payMethod
        holder.binding.tvPrice.text=differ.currentList[position].totalPrice
        holder.binding.btnCancelOrder.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.alertDeleteMessage)
            builder.setPositiveButton(R.string.yes) { _, _ ->
                viewModel.updateOrder(order)
            }
            builder.setNegativeButton(R.string.no, null)
            builder.show()

        }

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