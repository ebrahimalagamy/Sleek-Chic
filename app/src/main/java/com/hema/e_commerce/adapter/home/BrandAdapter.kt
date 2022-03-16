package com.hema.e_commerce.adapter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.RowBrandsBinding
import com.hema.e_commerce.model.dataclass.smartCollection.SmartCollection
import com.hema.e_commerce.util.Constant.BRAND_KEY
import com.hema.e_commerce.util.Constant.FLAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BrandAdapter(val brandList:ArrayList<SmartCollection>) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {
    lateinit var navController: NavController


    inner class ViewHolder(val binding: RowBrandsBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        navController = Navigation.findNavController(parent)
        return ViewHolder(RowBrandsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //bundle  to save brand name
        val brandBundle=Bundle().apply {
            putString(BRAND_KEY,brandList[position].title)
            putInt(FLAG,1)
        }

        holder.binding.brandTitle.text=brandList[position].title
        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(holder.binding.brandImageView.context).load(brandList[position].image.src).into(holder.binding.brandImageView)
        }

        holder.binding.root.setOnClickListener(View.OnClickListener {
            navController.navigate(
                R.id.action_home_to_typeListProductFragment2,brandBundle
            )
        })
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