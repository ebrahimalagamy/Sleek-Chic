package com.hema.e_commerce.adapter.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.databinding.RowProductBinding

class ProductsAdapter() : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var binding: RowProductBinding? = null

    inner class ViewHolder(private val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /*
    // util to see only change in  to refresh it only not like we give list and we refresh all list
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