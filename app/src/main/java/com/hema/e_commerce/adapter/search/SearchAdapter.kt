package com.hema.e_commerce.adapter.search


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
import com.hema.e_commerce.util.Constant.FLAG
import com.hema.e_commerce.util.Constant.PRODUCT


class SearchAdapter(private val productList: List<Product>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var navController: NavController


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
        val products = productList[position]
        Glide.with(holder.itemBinding.imgListProduct.context).load(products.image.src).into(holder.itemBinding.imgListProduct)
        holder.itemBinding.tvListShDesc.text = products.title

        holder.itemView.setOnClickListener{
            val bundle = bundleOf(PRODUCT to  products.id)
            navController.navigate(R.id.action_searchFragment_to_productFragment,bundle)

        }



    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemBinding: ItemProductListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var itemBinding: ItemProductListBinding = itemBinding

    }


}