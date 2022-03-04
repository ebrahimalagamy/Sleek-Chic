package com.hema.e_commerce.ui.category.categoryui.typelistofproduct

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentTypeListProductBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.model.dataclass.allProducts.ProductsResponse
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.viewmodels.ListOfProductsViewModel
import com.hema.e_commerce.ui.category.testmodels.TypeModelList
import java.util.*


class TypeListProductsFragment : Fragment() {
    lateinit var binding: FragmentTypeListProductBinding
    lateinit var adapter: TypeListAdapter
    lateinit var arr: ArrayList<ProductsResponse>
    lateinit var tempArray: ArrayList<TypeModelList>
    lateinit var viewModel:ListOfProductsViewModel
     var collectionsId:Long =398033617127
    lateinit var subCollectionsName:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_type_list_product, container, false)
        viewModel = ViewModelProvider(this).get(ListOfProductsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var bundle:Bundle?= arguments
        if(bundle!=null){
            Log.i("ccccc", "onCreateView: bundel ! null")

            if(bundle.getLong("subCollectionId")!=null && bundle.getString("subName")!=null) {
                bundle.getLong("subCollectionId")
                Log.i("ccc", "onCreateView: "+bundle.getLong("subCollectionId")+"-------"+bundle.getString("subName"))
                collectionsId=bundle.getLong("subCollectionId")
                subCollectionsName= bundle.getString("subName")!!

            }
            else{
                Log.i("ccc", "onCreateView: empty string ")
            }
        }

        else{

            Log.i("ssssssssssssssssss", "onCreateView:bundel = null ")


        }
        initViews(collectionsId)
        observe()

    }
    fun observe() {
        viewModel.SubCollectionsProductsLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

           var productList = it.products

            var list: MutableList<Product> = mutableListOf<Product>()
            for(i in productList){
                if(i.product_type.equals(subCollectionsName)){
                    list.add(i)

                }


            }

            adapter = TypeListAdapter(list, requireContext())
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recListProduct.adapter = adapter
            binding.recListProduct.layoutManager = layoutManager




//            var fragMan: FragmentManager? = fragmentManager
//            var categoriesList = it.custom_collections
//
//            Log.i(TAG, "onActivityCreated: " + categoriesList.size)
//            productAdapter = CategoriesProductAdapter(fragMan, categoriesList, requireContext())
//            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//            binding.recProduct.adapter = productAdapter
//            binding.recProduct.layoutManager = layoutManager



        })
    }

    private fun initViews(idSubCollections:Long) {
        viewModel.getSubCollectionsProducts(idSubCollections)
    }



}