package com.hema.e_commerce.adapter.catagory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.CategoriesProductsItemsBinding
import com.hema.e_commerce.model.dataclass.listofcustomcollections.CustomCollection
import com.hema.e_commerce.ui.category.categoryui.containerui.FragmentContainer


class CategoriesProductAdapter(
    private var fragMan: FragmentManager?,
    private val productList: List<CustomCollection>,
    val context: Context
) :
    RecyclerView.Adapter<CategoriesProductAdapter.ViewHolder>() {
    private lateinit var navController: NavController


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        navController = Navigation.findNavController(parent)

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.categories_products_items,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = productList[position]
            holder.itemBinding.productTypeName.text = products.title



        holder.itemBinding.productTypeName.setOnClickListener {

            // navController.navigate(R.id.action_category_to_fragmentContainer)

            val fragContainer = FragmentContainer()
                val bundle = Bundle()
              bundle.putString("title",products.title)

                fragContainer.arguments=bundle
            fragMan!!.beginTransaction().replace(R.id.fram_cont, fragContainer).commit()


        }


    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemBinding: CategoriesProductsItemsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var itemBinding: CategoriesProductsItemsBinding

        init {
            this.itemBinding = itemBinding
        }
    }


}