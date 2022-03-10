package com.hema.e_commerce.adapter.wishList

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemFavoriteBinding
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.viewmodels.WishListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishListAdapter(val context:Context,var viewModel:WishListViewModel) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

    inner class ViewHolder( val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favorite,
                parent,
                false
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

     /*
       //Error
       holder.binding.imageButton2.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.alertDeleteMessage)

            builder.setPositiveButton(R.string.yes) { _, _ ->
               // this.differ.currentList.remove(differ.currentList[position])
                this.viewModel.delete(differ.currentList[position])
                this.notifyDataSetChanged()
            }
            builder.setNegativeButton(R.string.no, null)
            builder.show()

        })*/

        holder.binding.btnAddToCart.setOnClickListener(View.OnClickListener {
        val cart=CartProductData(
                differ.currentList[position].id,differ.currentList[position].image,differ.currentList[position].title
        ,differ.currentList[position].price,differ.currentList[position].inventory_quantity, 1)
        viewModel.saveCartList(cart)
            //todo delete from fav after add to cart


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