package com.hema.e_commerce.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.databinding.RowBrandsBinding
import com.hema.e_commerce.model.dataclass.smartCollection.SmartCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BrandAdapter(val brandList:ArrayList<SmartCollection>) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: RowBrandsBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowBrandsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.brandTitle.text=brandList[position].title
        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(holder.binding.brandImageView.context).load(brandList[position].image.src).into(holder.binding.brandImageView)
        }
    }

    override fun getItemCount(): Int {
        return brandList.size
    }
    fun updateBrand(brandItem:List<SmartCollection>) {
        brandList.clear()
        brandList.addAll(brandItem)
        notifyDataSetChanged()
    }




}