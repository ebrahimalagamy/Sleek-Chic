package com.hema.e_commerce.ui.category.categoryui.typelistofproduct


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemProductListBinding
import com.hema.e_commerce.ui.category.testmodels.TypeModelList


class TypeListAdapter(val productList: ArrayList<TypeModelList>, val context: Context) :
    RecyclerView.Adapter<TypeListAdapter.ViewHolder>() {
    lateinit var navController: NavController



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //  navController=Navigation.findNavController(parent)
        // navController.navigate(R.id.action_category_to_fragmentContainer)


        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_list,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("mmmmmmmmmmmmmmmmmmmmmm", "onBindViewHolder: " + productList.size)
        val products = productList.get(position)
        holder.itemBinding.tvListPrice.text = products.price
        holder.itemBinding.imgListProduct.setImageResource(products.img)
        holder.itemBinding.tvListShDesc.text = products.desc


        //  holder.itemBinding.tvNameContainerItemProduct.setOnClickListener{

        // navController.navigate(R.id.action_category_to_fragmentContainer)

        //       containerInterface.sendData("aya")


//            var fragContainer :FragmentContainer=FragmentContainer()
//            var  bundle:Bundle= Bundle()
//            bundle.putString("selected data",products.name)
//
//            fragContainer.arguments=bundle
//            //    FragmentManager().beginTransaction().replace(R.id.fram_cont, fragContainer)
//            val  manager: FragmentManager
//            fragMan!!.beginTransaction().replace(R.id.fram_cont, fragContainer).commit()
//
        //   }


    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class ViewHolder(itemBinding: ItemProductListBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: ItemProductListBinding

        init {
            this.itemBinding = itemBinding
        }
    }




}