package com.hema.e_commerce.adapter.catagory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ContainerItemBinding
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections
import com.hema.e_commerce.util.Constant.FLAG
import com.hema.e_commerce.util.Constant.SUB_COLLECTION_ID
import com.hema.e_commerce.util.Constant.SUB_COLLECTION_NAME


class ContainerAdapter(
    var subCollectionsId: Long,
    fragMan: FragmentManager?,
    val productList: ArrayList<SubCollections>,
    val context: Context,
) :
    RecyclerView.Adapter<ContainerAdapter.ViewHolder>() {
    lateinit var navController: NavController


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        navController = Navigation.findNavController(parent)

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.container_item,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val products = productList.get(position)
        holder.itemBinding.tvNameContainerItemProduct.text = products.subName
             holder.itemBinding.imgItemContainerProduct.setImageResource(products.image)


        holder.itemView.setOnClickListener {
            val bundle = bundleOf(SUB_COLLECTION_NAME to  products.subName,SUB_COLLECTION_ID to subCollectionsId
                ,FLAG to 0)
           navController.navigate(R.id.action_category_to_typeListProductFragment2,bundle)


        }



    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemBinding: ContainerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: ContainerItemBinding

        init {
            this.itemBinding = itemBinding
        }
    }


}