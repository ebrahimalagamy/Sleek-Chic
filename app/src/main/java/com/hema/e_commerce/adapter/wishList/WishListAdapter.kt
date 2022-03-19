package com.hema.e_commerce.adapter.wishList

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemFavoriteBinding
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.viewmodels.WishListViewModel
import com.hema.e_commerce.util.Constant
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishListAdapter(val context:Context,var viewModel:WishListViewModel) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    private lateinit var navController: NavController
    private lateinit var sharedPref: SharedPreferencesProvider


    inner class ViewHolder( val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        navController= Navigation.findNavController(parent)
        sharedPref = SharedPreferencesProvider(context)
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_favorite, parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitlefav.text = differ.currentList[position].title
        //Handle Currency
        val sharedPreferences: SharedPreferences =context.getSharedPreferences("currency", 0)
        var value = sharedPreferences.getString("currency","EGP")
        when(value){
            "USA"-> {
                var usCurrancy=   ((differ.currentList[position].price).toDouble() / (15.71))
                val number:Double=String.format("%.2f",usCurrancy).toDouble()
                holder.binding.tvPrice.text = number.toString()+" "+"$"

            }
            "EUR"->      {
                var ureCurrancy=   ((differ.currentList[position].price).toDouble() / (17.10))
                val number:Double=String.format("%.2f",ureCurrancy).toDouble()
                holder.binding.tvPrice.text=number.toString()+" "+"EUR"
            }
            else->  holder.binding.tvPrice.text = differ.currentList[position].price+" "+"EGP"

        }

      //  holder.binding.tvDescription.text = differ.currentList[position].body_html

        CoroutineScope(Dispatchers.Main).launch{
            Glide.with(holder.binding.imgFavProduct.context)
                .load(differ.currentList[position].image).into(holder.binding.imgFavProduct)
        }

        holder.itemView.setOnClickListener{
            val bundle = bundleOf(Constant.PRODUCT to  differ.currentList[position].id)

            navController.navigate(R.id.productFragment,bundle)

        }

        holder.binding.btnAddToCart.setOnClickListener(View.OnClickListener {
        val cart=CartProductData(
            differ.currentList[position].id,
            sharedPref.getUserInfo().customer?.customerId,
            differ.currentList[position].image,
            differ.currentList[position].title,
            differ.currentList[position].price,
            differ.currentList[position].inventory_quantity,
            1)
        viewModel.saveCartList(cart)


        })

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    // util to see only change in articles to refresh it only not like we give list and we refresh all list
    private val differCallBack = object : DiffUtil.ItemCallback<FavoriteProduct>(){
        override fun areItemsTheSame(oldItem: FavoriteProduct,newItem: FavoriteProduct):Boolean{
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem == newItem
        }
    }

    //Async list differ take two list and compare them to change the difference only it run on background
    val differ= AsyncListDiffer(this,differCallBack)


}