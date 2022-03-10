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
import com.hema.e_commerce.databinding.RowProductBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.util.Constant.PRODUCT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsAdapter(private val productList:ArrayList<Product>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private lateinit var navController: NavController


    inner class ViewHolder( val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        navController = Navigation.findNavController(parent)

        return ViewHolder(
            RowProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //bundle  to save productId
        val bundle= Bundle().apply {
            putLong(PRODUCT,productList[position].id)
        }
//        holder.binding.tvBrand.text=productList[position].vendor
//        holder.binding.tvPrice.text= productList[position].variants[0].price
        holder.binding.tvProductname.text=productList[position].title
        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(holder.binding.imgProduct.context).load(productList[position].image.src).into(holder.binding.imgProduct)
        }

        holder.binding.root.setOnClickListener(View.OnClickListener {
            navController.navigate(
                R.id.action_home_to_productFragment,bundle
            )
        })

    }

    override fun getItemCount(): Int {
        return productList.size
    }
    fun updateproduct(productItem:List<Product>) {
        productList.clear()
        productList.addAll(productItem)
        notifyDataSetChanged()
    }

}
