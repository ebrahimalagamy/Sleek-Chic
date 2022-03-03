package com.hema.e_commerce.adapter.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.databinding.RowProductBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsAdapter(val productList:ArrayList<Product>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {


    inner class ViewHolder( val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvBrand.text=productList[position].vendor
//        holder.binding.tvPrice.text= productList[position].variants[0].price
        holder.binding.tvProductName.text=productList[position].title
        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(holder.binding.ImageProduct.context).load(productList[position].image.src).into(holder.binding.ImageProduct)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }
    fun updateproduct(productItem:List<Product>) {
        productList.clear()
        productList.addAll(productItem)
        notifyDataSetChanged()
    }

   /* private val differCallBack = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product,newItem:Product):Boolean{
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    //Async list differ take two list and compare them to change the difference only it run on background
    val differ= AsyncListDiffer(this,differCallBack)*/
}
