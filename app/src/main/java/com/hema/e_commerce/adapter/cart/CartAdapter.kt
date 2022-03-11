package com.hema.e_commerce.adapter.cart

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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

class CartAdapter(val cartViewModel: CartViewModel) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private val count= MutableLiveData<Int>()
    private val ChangeQuntityListener = MutableLiveData<Boolean>()
    private val order = MutableLiveData<Long>()
    private val favOrder = MutableLiveData<CartProductData>()

    inner class ViewHolder(itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.getRoot()) {
        var itemCartBinding: ItemCartBinding

        init {
            this.itemCartBinding = itemCartBinding
        }




    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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



        val sharedPreferences: SharedPreferences = holder.itemCartBinding.minCount.context.getSharedPreferences("currency", 0)
        var value = sharedPreferences.getString("currency","EGP")
        Log.i("cur value", "onBindViewHolder: "+value)


        when(value){
            "EGP"-> {
                holder.itemCartBinding.price.text =
                    differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.eg)
                Log.i("currancyyyyyy", "onBindViewHolder: "+ differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                    R.string.eg))

            }
            "USA"-> {
                var usCurrancy=   (( differ.currentList[position].price).toDouble() / (15.71))
                val number:Double=String.format("%.2f",usCurrancy).toDouble()
                holder.itemCartBinding.price.text = number.toString() + " " + holder.itemCartBinding.minCount.context.getString(R.string.us)
                Log.i("currancyyyyyy", "onBindViewHolder: "+         number.toString() + " " + holder.itemCartBinding.minCount.context.getString(R.string.us)
                )


            }
            "EUR"->      {
                var ureCurrancy=   ((differ.currentList[position].price).toDouble() / (17.10))
                val number:Double=String.format("%.2f",ureCurrancy).toDouble()
                holder.itemCartBinding.price.text=number.toString()+" "+ holder.itemCartBinding.minCount.context.getString(R.string.eur)

                Log.i("currancyyyyyy", "onBindViewHolder: "+number.toString()+" "+ holder.itemCartBinding.minCount.context.getString(R.string.eur)
                )}
            else -> {
                holder.itemCartBinding.price.text =
                    differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                        R.string.eg)
                Log.i("currancyyyyyy", "onBindViewHolder: else "+  differ.currentList[position].price + " " + holder.itemCartBinding.minCount.context.getString(
                    R.string.eg))
            }
        }


        //
        holder.itemCartBinding.copoun.text = "free delevery"
        Glide.with(holder.itemCartBinding.imageView)
            .load(differ.currentList[position].image)
            .fitCenter()
            .placeholder(R.drawable.ic_loading)
            .into(holder.itemCartBinding.imageView)


        holder.itemCartBinding.minCount.setOnClickListener {
            var num =((holder.itemCartBinding.count.text.toString().toInt())-1)
            if(num>0){
                // differ.currentList[position].count-= num
                differ.currentList[position].inventory_quantity=num







              //  differ.currentList[position].price
                //
                holder.itemCartBinding.count.text=num.toString()

                val cartitem= CartProductData(
                    differ.currentList[position].id,
                    differ.currentList[position].image,differ.currentList[position].title
                    ,differ.currentList[position].price
                    ,differ.currentList[position].inventory_quantity,num
                )
                cartViewModel.updateItemCount(cartitem)

                onChangeQuntity()
                onCountChange(num)
            }

            else{
                onDelClick( differ.currentList[position].id)
            }

        }

        holder.itemCartBinding.increCount.setOnClickListener {
            var num =((holder.itemCartBinding.count.text.toString().toInt())+1)
            differ.currentList[position].inventory_quantity=num
            differ.currentList[position].price
            holder.itemCartBinding.count.text=num.toString()
            val cartitem= CartProductData(
                differ.currentList[position].id,
                differ.currentList[position].image,differ.currentList[position].title
                ,differ.currentList[position].price
                ,differ.currentList[position].inventory_quantity,num
            )
            cartViewModel.updateItemCount(cartitem)

            onChangeQuntity()
            onCountChange(num)


        }


        holder.itemCartBinding.favBt.setOnClickListener {
            val fav=FavoriteProduct(
                differ.currentList[position].id,differ.currentList[position].image,differ.currentList[position].title
                ,differ.currentList[position].price,differ.currentList[position].inventory_quantity, 1)
            cartViewModel.insertFav(fav)



        }



        holder.itemCartBinding.cancel.setOnClickListener {
            cartViewModel.deleteOneItemCart(cartProduct)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun onCountChange(item:Int){
        count.postValue(item)
    }




    fun onChangeQuntity() {
        ChangeQuntityListener.postValue(true)
    }
    fun onDelClick(id: Long) {
        order.postValue(id)
    }

    fun onFavClick(item: CartProductData) {
        favOrder.postValue(item)
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