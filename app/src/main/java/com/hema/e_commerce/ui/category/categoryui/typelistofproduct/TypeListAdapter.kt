package com.hema.e_commerce.ui.category.categoryui.typelistofproduct


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemProductListBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.ui.category.testmodels.TypeModelList


class TypeListAdapter(val productList: List<Product>, val context: Context) :
    RecyclerView.Adapter<TypeListAdapter.ViewHolder>() {
    lateinit var navController: NavController


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         navController= Navigation.findNavController(parent)

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_list,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = productList.get(position)
//        holder.itemBinding.tvListPrice.text = products.variants.get(0).price
        Glide.with(context).load(products.image.src).into(holder.itemBinding.imgListProduct)

        holder.itemBinding.tvListShDesc.text = products.title
        holder.itemView.setOnClickListener{
            val bundle = bundleOf("productId" to  products.id)
            navController.navigate(R.id.action_typeListProductFragment2_to_productFragment,bundle)

        }



    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemBinding: ItemProductListBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: ItemProductListBinding

        init {
            this.itemBinding = itemBinding
        }
    }


}