package com.hema.e_commerce.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.databinding.RowBrandsBinding

class BrandAdapter: RecyclerView.Adapter<BrandAdapter.ViewHolder>()  {
    private var binding: RowBrandsBinding? = null

    inner class ViewHolder(private val binding: RowBrandsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RowBrandsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}