package com.hema.e_commerce.ui.category.categoryui.containerui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ContainerItemBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.ui.category.testmodels.SubCollections


class ContainerAdapter(
    var fragMan: FragmentManager?,
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
        Log.i("mmmmmmmmmmmmmmmmmmmmmm", "onBindViewHolder: " + productList.size)


        val products = productList.get(position)




        holder.itemBinding.tvNameContainerItemProduct.text = products.subName
             holder.itemBinding.imgItemContainerProduct.setImageResource(products.image)


        holder.itemView.setOnClickListener {
            navController.navigate(R.id.action_category_to_typeListProductFragment2)

        }


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


    class ViewHolder(itemBinding: ContainerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: ContainerItemBinding

        init {
            this.itemBinding = itemBinding
        }
    }


}