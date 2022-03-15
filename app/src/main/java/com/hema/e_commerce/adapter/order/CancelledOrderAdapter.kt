package com.hema.e_commerce.adapter.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemOrderBinding
import com.hema.e_commerce.model.room.orderroom.OrderData
import com.hema.e_commerce.model.viewmodels.OrderFragmentViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider

class CancelledOrderAdapter (val list: List<OrderData>, val viewModel: OrderFragmentViewModel, val context: Context) : RecyclerView.Adapter<CancelledOrderAdapter.ViewHolder>() {
    lateinit var order:OrderData
    private lateinit var sharedPref: SharedPreferencesProvider

    inner class ViewHolder( val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        sharedPref = SharedPreferencesProvider(context)
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_order, parent, false
        )
        )
    }

    override fun getItemCount(): Int {
        return   list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
             order = OrderData(
                 list[position].orderNumber,
                 sharedPref.getUserInfo().customer?.customerId,
                 list[position].totalPrice,
                 list[position].customerName,
                 list[position].address,
                 list[position].phone,
                 list[position].payMethod,
                "ACTIVE"
            )

            holder.binding.tvOrderNumber.text=list[position].orderNumber.toString()
        holder.binding.tvState.text=list[position].state

        holder.binding.tvAddress.text=list[position].address
        holder.binding.tvName.text=list[position].customerName
        holder.binding.tvPhone.text=list[position].phone

        holder.binding.tvPayment.text=list[position].payMethod
        holder.binding.tvPrice.text=list[position].totalPrice
        holder.binding.btnCancelOrder.text="Active Order"
        holder.binding.imageButton2

        holder.binding.imageButton2.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.alertdelteMessage)
            builder.setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteOrder(order)
            }
            builder.setNegativeButton(R.string.no, null)
            builder.show()
        }

        holder.binding.btnCancelOrder.setOnClickListener {

                val builder = AlertDialog.Builder(context)
                builder.setMessage(R.string.alertActiveMessage)
                builder.setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.updateOrder(order)
                }
                builder.setNegativeButton(R.string.no, null)
                builder.show()


        }
    }


}