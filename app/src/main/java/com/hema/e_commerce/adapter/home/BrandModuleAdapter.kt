package com.hema.e_commerce.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.RowBrandsBinding
import com.hema.e_commerce.ui.home.BrandModel


class BrandModuleAdapter(private val brand: BrandModel) : RecyclerView.Adapter<BrandModuleAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowBrandsBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_brands,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binding.brandTitle.text = brand.brand
        holder.binding.brandImageView.setImageResource( brand.image)
    }

    override fun getItemCount(): Int {
        return 4
    }



}