package com.hema.e_commerce.adapter.productList


<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/typelistofproduct/TypeListAdapter.kt
import android.content.Context
import android.util.Log
=======
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/productList/TypeListAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ItemProductListBinding
<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/typelistofproduct/TypeListAdapter.kt
import com.hema.e_commerce.ui.category.testmodels.TypeModelList


class TypeListAdapter(val productList: ArrayList<TypeModelList>, val context: Context) :
    RecyclerView.Adapter<TypeListAdapter.ViewHolder>() {
    lateinit var navController: NavController
=======
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.util.Constant.FLAG
import com.hema.e_commerce.util.Constant.PRODUCT


class TypeListAdapter(private val productList: List<Product>) : RecyclerView.Adapter<TypeListAdapter.ViewHolder>() {
    private lateinit var navController: NavController
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/productList/TypeListAdapter.kt



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
<<<<<<< Updated upstream:app/src/main/java/com/hema/e_commerce/ui/category/categoryui/typelistofproduct/TypeListAdapter.kt
        Log.i("mmmmmmmmmmmmmmmmmmmmmm", "onBindViewHolder: " + productList.size)
        val products = productList.get(position)
        holder.itemBinding.tvListPrice.text = products.price
        holder.itemBinding.imgListProduct.setImageResource(products.img)
        holder.itemBinding.tvListShDesc.text = products.desc

=======
        val products = productList[position]
//        holder.itemBinding.tvListPrice.text = products.variants.get(0).price
        Glide.with(holder.itemBinding.imgListProduct.context).load(products.image.src).into(holder.itemBinding.imgListProduct)
        holder.itemBinding.tvListShDesc.text = products.title

        holder.itemView.setOnClickListener{
            val bundle = bundleOf(PRODUCT to  products.id, FLAG to 1)
            navController.navigate(R.id.action_typeListProductFragment2_to_productFragment,bundle)
>>>>>>> Stashed changes:app/src/main/java/com/hema/e_commerce/adapter/productList/TypeListAdapter.kt

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
        RecyclerView.ViewHolder(itemBinding.root) {
        var itemBinding: ItemProductListBinding = itemBinding

    }




}