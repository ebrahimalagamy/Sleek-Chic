package com.hema.e_commerce.ui.category.categoryui.maincategoryui

import android.content.Context
import android.os.Bundle
import android.util.Log
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
    var fragMan: FragmentManager?,
    val productList: List<CustomCollection>,
    val context: Context
) :
    RecyclerView.Adapter<CategoriesProductAdapter.ViewHolder>() {
    lateinit var navController: NavController


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
        val products = productList.get(position)
        holder.itemBinding.productTypeName.text = products.title

        holder.itemBinding.productTypeName.setOnClickListener {

            // navController.navigate(R.id.action_category_to_fragmentContainer)


            var fragContainer: FragmentContainer = FragmentContainer()
                var  bundle: Bundle = Bundle()
              bundle.putString("title",products.title)

                fragContainer.arguments=bundle
            val manager: FragmentManager
            fragMan!!.beginTransaction().replace(R.id.fram_cont, fragContainer).commit()


        }


    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemBinding: CategoriesProductsItemsBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: CategoriesProductsItemsBinding

        init {
            this.itemBinding = itemBinding
        }
    }
    }
