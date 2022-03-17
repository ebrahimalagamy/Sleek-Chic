package com.hema.e_commerce.adapter.cart

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemCartBinding
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.viewmodels.CartViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider

class CartAdapter(private val cartViewModel: CartViewModel,private val context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var sharedPref: SharedPreferencesProvider

    inner class ViewHolder(itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root) {
        var itemCartBinding: ItemCartBinding = itemCartBinding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        sharedPref = SharedPreferencesProvider(context)
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cart,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cartProduct = differ.currentList[position]
        holder.itemCartBinding.title.text = differ.currentList[position].title
        // holder.itemCartBinding.price.text = differ.currentList[position].price

        val sharedPreferences: SharedPreferences =
            holder.itemCartBinding.minCount.context.getSharedPreferences("currency", 0)
        var value = sharedPreferences.getString("currency", "EGP")
        when (value) {
            "EGP" -> {
                holder.itemCartBinding.price.text =
                    differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.eg
                    )
                Log.i(
                    "currancyyyyyy",
                    "onBindViewHolder: " + differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.eg
                    )
                )

            }
            "USA" -> {
                var usCurrancy = ((differ.currentList[position].price).toDouble() / (15.71))
                val number: Double = String.format("%.2f", usCurrancy).toDouble()
                holder.itemCartBinding.price.text =
                    number.toString() + " " + holder.itemCartBinding.minCount.context.getString(R.string.us)
                Log.i(
                    "currancyyyyyy",
                    "onBindViewHolder: " + number.toString() + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.us
                    )
                )


            }
            "EUR" -> {
                var ureCurrancy = ((differ.currentList[position].price).toDouble() / (17.10))
                val number: Double = String.format("%.2f", ureCurrancy).toDouble()
                holder.itemCartBinding.price.text =
                    number.toString() + " " + holder.itemCartBinding.minCount.context.getString(R.string.eur)

                Log.i(
                    "currancyyyyyy",
                    "onBindViewHolder: " + number.toString() + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.eur
                    )
                )
            }
            else -> {
                holder.itemCartBinding.price.text =
                    differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.eg
                    )
                Log.i(
                    "currancyyyyyy",
                    "onBindViewHolder: else " + differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.eg
                    )
                )
            }
        }
        holder.itemCartBinding.copoun.text = "free delevery"
        Glide.with(holder.itemCartBinding.imageView)
            .load(differ.currentList[position].image)
            .fitCenter()
            .placeholder(R.drawable.ic_loading)
            .into(holder.itemCartBinding.imageView)


        holder.itemCartBinding.tvcount.text = differ.currentList[position].count.toString()
        holder.itemCartBinding.minCount.setOnClickListener {
            if (holder.itemCartBinding.tvcount.text.toString().toInt() >1) {
                var counter = holder.itemCartBinding.tvcount.text.toString().toInt()
                counter -= 1
                val cartitem = CartProductData(
                    differ.currentList[position].id,
                    sharedPref.getUserInfo().customer?.customerId,
                    differ.currentList[position].image,
                    differ.currentList[position].title,
                    differ.currentList[position].price,
                    differ.currentList[position].inventory_quantity,
                    counter)
                cartViewModel.updateItemCount(cartitem)
                cartViewModel.onChangeQuntity()
                cartViewModel.onCountChange(counter)
            }
        }
        holder.itemCartBinding.increCount.setOnClickListener {
                if (holder.itemCartBinding.tvcount.text.toString().toInt() < differ.currentList[position].inventory_quantity) {
                var counter = holder.itemCartBinding.tvcount.text.toString().toInt()
                counter += 1
                    val cartitem = CartProductData(
                        differ.currentList[position].id,
                        sharedPref.getUserInfo().customer?.customerId,
                        differ.currentList[position].image,
                        differ.currentList[position].title,
                        differ.currentList[position].price,
                        differ.currentList[position].inventory_quantity,
                        counter)
                    cartViewModel.updateItemCount(cartitem)
                    cartViewModel.onChangeQuntity()
                    cartViewModel. onCountChange(counter)
            }else{
                Toast.makeText(context, "No More Pieces In Inventory", Toast.LENGTH_SHORT).show() }
        }


        holder.itemCartBinding.favBt.setOnClickListener {
            val fav = FavoriteProduct(
                differ.currentList[position].id,
                sharedPref.getUserInfo().customer?.customerId,
                differ.currentList[position].image,
                differ.currentList[position].title,
                differ.currentList[position].price,
                differ.currentList[position].inventory_quantity,
                1
            )
              favAlart(fav)
        }


        holder.itemCartBinding.cancel.setOnClickListener {

        deleteAlert(cartProduct)


        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private fun deleteAlert(cartProductData: CartProductData) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("This Item will Deleted from cart")
        builder.setMessage("Are You Sure ?")
        builder.setPositiveButton("Yes") { _, _ ->
            cartViewModel.deleteOnItemCart(cartProductData)
            cartViewModel.deleteItem = MutableLiveData<CartProductData>()
        }
        builder.setNegativeButton("No") { _, _ ->
            cartViewModel.deleteItem = MutableLiveData<CartProductData>()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.YELLOW)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

    private fun favAlart(favoriteProduct: FavoriteProduct) {
      //  val builder = AlertDialog.Builder(context)
        cartViewModel.insertFav(favoriteProduct)
        Toast.makeText(context, R.string.addToFavorite, Toast.LENGTH_SHORT).show()

        /*  builder.setTitle("This Product add to favouirt ")
          builder.setMessage("Go To Favourit Page To see It !!!! ")
          builder.setPositiveButton("OK") { _, _ ->
            //  cartViewModel.deleteItem = MutableLiveData<CartProductData>()
          }
          val alertDialog: AlertDialog = builder.create()
          alertDialog.setCancelable(false)
          alertDialog.show()
          alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.YELLOW)
          alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)*/
    }


    // util to see only change in articles to refresh it only not like we give list and we refresh all list
    private val differCallBack = object : DiffUtil.ItemCallback<CartProductData>(){
        override fun areItemsTheSame(oldItem: CartProductData,newItem:CartProductData):Boolean{
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CartProductData, newItem: CartProductData): Boolean {
            return oldItem == newItem
        }
    }

    //Async list differ take two list and compare them to change the difference only it run on background
    val differ= AsyncListDiffer(this,differCallBack)


}