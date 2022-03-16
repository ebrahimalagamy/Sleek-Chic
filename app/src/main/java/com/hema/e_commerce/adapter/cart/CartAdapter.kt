package com.hema.e_commerce.adapter.cart

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemCartBinding
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.viewmodels.CartViewModel
import com.hema.e_commerce.model.viewmodels.CurrancyViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider

class CartAdapter(private val currencyViewModel: CurrancyViewModel,private val cartViewModel: CartViewModel,private val context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var sharedPref: SharedPreferencesProvider
    var currancy=context.getString(R.string.eg)


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
                    currancy=context.getString(R.string.eg)

                initViews("EGP",(differ.currentList[position].price).toDouble() )
                currancyObserve(holder)

            }
            "USA" -> {
                currancy=context.getString(R.string.us)

                //val number: Double = String.format("%.2f", usCurrancy).toDouble()
                initViews("USD",(differ.currentList[position].price).toDouble() )

                currancyObserve(holder)

            }
            "EUR" -> {
                currancy=context.getString(R.string.eur)

                initViews("EUR",(differ.currentList[position].price).toDouble() )

                currancyObserve(holder)
            }
            else -> {
                currancy=context.getString(R.string.eg)

                initViews("EGP",(differ.currentList[position].price).toDouble() )
                currancyObserve(holder)
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
            cartViewModel.insertFav(fav)
        }


        holder.itemCartBinding.cancel.setOnClickListener {
            cartViewModel.deleteOneItemCart(cartProduct)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
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
//currancy


    private fun initViews( to: String,amount:Double) {
        currencyViewModel.changeCurrancy(to, amount)
    }

    fun currancyObserve(holder: ViewHolder){
        currencyViewModel.currancyLiveData.observeForever{


            val number: Double = String.format("%.2f", it.result).toDouble()
            holder.itemCartBinding.price.text =number.toString()+currancy


        }

    }}