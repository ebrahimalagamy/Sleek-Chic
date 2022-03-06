package com.hema.e_commerce.adapter.catagory

import android.content.Context
<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/maincategoryui/CategoriesProductAdapter.kt
import android.util.Log
=======
import android.os.Bundle
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/catagory/CategoriesProductAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.CategoriesProductsItemsBinding
import com.hema.e_commerce.ui.category.categoryui.containerui.FragmentContainer
import com.hema.e_commerce.ui.category.testmodels.model


class CategoriesProductAdapter(
<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/maincategoryui/CategoriesProductAdapter.kt
    var fragMan: FragmentManager?,
    val productList: ArrayList<model>,
=======
    private var fragMan: FragmentManager?,
    private val productList: List<CustomCollection>,
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/catagory/CategoriesProductAdapter.kt
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
<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/maincategoryui/CategoriesProductAdapter.kt
        Log.i("mmmmmmmmmmmmmmmmmmmmmm", "onBindViewHolder: " + productList.size)
        val products = productList.get(position)
        holder.itemBinding.productTypeName.text = products.name
=======
        val products = productList[position]
            holder.itemBinding.productTypeName.text = products.title


>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/catagory/CategoriesProductAdapter.kt

        holder.itemBinding.productTypeName.setOnClickListener {

            // navController.navigate(R.id.action_category_to_fragmentContainer)

<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/maincategoryui/CategoriesProductAdapter.kt
=======
            val fragContainer = FragmentContainer()
                val bundle = Bundle()
              bundle.putString("title",products.title)
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/catagory/CategoriesProductAdapter.kt

            var fragContainer: FragmentContainer = FragmentContainer()
//                var  bundle:Bundle= Bundle()
//                bundle.putString("selected data",products.name)
//
//                fragContainer.arguments=bundle
            //    FragmentManager().beginTransaction().replace(R.id.fram_cont, fragContainer)
            val manager: FragmentManager
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