package com.hema.e_commerce.adapter.address

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemAdressesBinding
import com.hema.e_commerce.model.dataclass.customer.AddressesItem
import com.hema.e_commerce.ui.settings.address.AddressDirections

class SelectedAdapter(var addressList: List<AddressesItem?>, val context: Context ) :
    RecyclerView.Adapter<SelectedAdapter.AddressVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressVH =
        AddressVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_adresses, parent, false
            )
        )

//    interface OnItemClickListener {
//        fun onItemClick(item: AddressesItem?)
//    }

    override fun onBindViewHolder(holder: AddressVH, position: Int) {
        holder.recyclerAddressBinding.address = addressList[position]
        holder.recyclerAddressBinding.tvEditAddress.setOnClickListener {
            val action = AddressDirections.actionAddressToEditAddress(addressList[position]!!)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun removeItem(position: Int): Long {
        val list = addressList.toMutableList()
        val id = addressList[position]?.id
        list.removeAt(position)
        addressList = list
        notifyItemRemoved(position)
        return id!!
    }

    override fun getItemCount() = addressList.size

    inner class AddressVH(
        val recyclerAddressBinding: ItemAdressesBinding
    ) : RecyclerView.ViewHolder(recyclerAddressBinding.root) {
    }
}