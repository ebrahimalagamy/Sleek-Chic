package com.hema.e_commerce.adapter.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.databinding.RowProductBinding

class ProductsAdapter() : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
=======
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
>>>>>>> Stashed changes

    private var binding: RowProductBinding? = null

    inner class ViewHolder(private val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
<<<<<<< Updated upstream
        binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
=======
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
        holder.binding.tvBrand.text=productList[position].vendor
//        holder.binding.tvPrice.text= productList[position].variants[0].price
        holder.binding.tvProductName.text=productList[position].title
        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(holder.binding.ImageProduct.context).load(productList[position].image.src).into(holder.binding.ImageProduct)
        }
        holder.binding.root.setOnClickListener(View.OnClickListener {
            navController.navigate(
                R.id.action_home_to_productFragment,bundle
            )
        })

>>>>>>> Stashed changes
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

<<<<<<< Updated upstream
    ////////////////////////////////////////////////////////////////////////////////////
    /*
    // util to see only change in articles to refresh it only not like we give list and we refresh all list
    private val differCallBack = object : DiffUtil.ItemCallback</*product*/ >(){
        override fun areItemsTheSame(oldItem: /*product*/,newItem:/*product*/):Boolean{
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: /*product*/, newItem: /*product*/): Boolean {
            return oldItem == newItem
        }
    }

    //Async list differ take two list and compare them to change the difference only it run on background
    val differ= AsyncListDiffer(this,differCallBack)
*/
}
=======
}
>>>>>>> Stashed changes
