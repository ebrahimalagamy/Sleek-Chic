package com.hema.e_commerce.adapter.productList


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemOrderBinding
import com.hema.e_commerce.databinding.ItemProductListBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.viewmodels.ListOfProductsViewModel
import com.hema.e_commerce.util.Constant.FLAG
import com.hema.e_commerce.util.Constant.PRODUCT


class TypeListAdapter(private val productList: List<Product>,private val viewModel: ListOfProductsViewModel) : RecyclerView.Adapter<TypeListAdapter.ViewHolder>() {
    private lateinit var navController: NavController
    var isFavBtnClicked: Boolean = false

    inner class ViewHolder( val itemBinding: ItemProductListBinding) : RecyclerView.ViewHolder(itemBinding.root)


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
      /*  val price=products.variants[0].price
        if (price==null){
            price==""
        }
        val favProduct=FavoriteProduct(products.id,products.image.src,products.title,price
        ,products.variants[0].inventory_quantity,1)*/
//        holder.itemBinding.tvListPrice.text = products.variants.get(0).price

        Glide.with(holder.itemBinding.imgListProduct.context).load(products.image.src).into(holder.itemBinding.imgListProduct)
        holder.itemBinding.tvListShDesc.text = products.title

        holder.itemView.setOnClickListener{
            val bundle = bundleOf(PRODUCT to  products.id)
            navController.navigate(R.id.productFragment,bundle)

        }
       /* holder.itemBinding.imageButton.setOnClickListener {


        }*/

     /*   holder.itemBinding.imageButton.setOnClickListener {
            isFavBtnClicked = if (isFavBtnClicked) {
                viewModel.deleteByID(products.id ?: 0)
                false
            } else {
                viewModel.insertFav(favProduct)
                true
            }
            setStoredButton(isFavBtnClicked,holder.itemBinding)
        }*/




    }

    override fun getItemCount(): Int {
        return productList.size
    }




/*
    private fun setStoredButton(isFavBtnClicked: Boolean,itemBinding: ItemProductListBinding) {
        if (isFavBtnClicked) {
            itemBinding.imageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            itemBinding.imageButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }*/

}