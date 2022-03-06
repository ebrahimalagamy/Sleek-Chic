package com.hema.e_commerce.adapter.catagory

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
<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/containerui/ContainerAdapter.kt
import com.hema.e_commerce.ui.category.testmodels.ModelContainer
=======
import com.hema.e_commerce.ui.category.subcollectionsmodel.SubCollections
import com.hema.e_commerce.util.Constant.FLAG
import com.hema.e_commerce.util.Constant.SUB_COLLECTION_ID
import com.hema.e_commerce.util.Constant.SUB_COLLECTION_NAME
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/catagory/ContainerAdapter.kt


class ContainerAdapter(
    var fragMan: FragmentManager?,
    val productList: ArrayList<ModelContainer>,
    val context: Context
) :
    RecyclerView.Adapter<ContainerAdapter.ViewHolder>() {
    lateinit var navController: NavController


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //  navController=Navigation.findNavController(parent)
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
        holder.itemBinding.tvNameContainerItemProduct.text = products.name
        holder.itemBinding.imgItemContainerProduct.setImageResource(products.img)


        holder.itemView.setOnClickListener {
<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/containerui/ContainerAdapter.kt
            navController.navigate(R.id.action_category_to_typeListProductFragment2)
=======
            val bundle = bundleOf(SUB_COLLECTION_NAME to  products.subName,SUB_COLLECTION_ID to subCollectionsId
                ,FLAG to 0)
           navController.navigate(R.id.action_category_to_typeListProductFragment2,bundle)
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/catagory/ContainerAdapter.kt

        }

<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/containerui/ContainerAdapter.kt

        //  holder.itemBinding.tvNameContainerItemProduct.setOnClickListener{

        // navController.navigate(R.id.action_category_to_fragmentContainer)

        //       containerInterface.sendData("aya")

=======
        }
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/catagory/ContainerAdapter.kt

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