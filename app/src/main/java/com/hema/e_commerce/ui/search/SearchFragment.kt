package com.hema.e_commerce.ui.search

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.search.SearchAdapter
import com.hema.e_commerce.databinding.FragmentTypeListProductBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.model.viewmodels.ListOfProductsViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {
    lateinit var binding: FragmentTypeListProductBinding
    lateinit var adapter: SearchAdapter
    lateinit var viewModel: ListOfProductsViewModel
    lateinit var products:List<Product>
     var searchProducts:ArrayList<Product> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_type_list_product, container, false)
        binding.searchView.isIconified = false
        viewModel = ViewModelProvider(this)[ListOfProductsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        brandList()
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //win press on search not to crush so repeat the logic of text change
                searchProducts.clear()
                val searchText= query!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    products.forEach {
                        if (it.title.lowercase(Locale.getDefault()).contains(searchText)
                            || it.vendor.lowercase(Locale.getDefault()).contains(searchText)){
                            searchProducts.add(it)
                        }
                    }
                    brandList()
                }else{
                    searchProducts.clear()
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //change when writing this the main logic
                searchProducts.clear()
                val searchText= newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    products.forEach {
                        if (it.title.lowercase(Locale.getDefault()).contains(searchText)
                            || it.vendor.lowercase(Locale.getDefault()).contains(searchText)){
                            searchProducts.add(it)
                        }
                    }
                    brandList()
                }else{
                    searchProducts.clear()
                }

            return false
            }

        })

    }


//call all product
    fun brandList(){
        initViews()
        observeSearchProduct()
    }

    private fun observeSearchProduct() {
        viewModel.allProduct .observe(viewLifecycleOwner, Observer {
            products= it.products
            adapter = SearchAdapter(searchProducts)
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recListProduct.adapter = adapter
            binding.recListProduct.layoutManager = layoutManager

        })
    }

    private fun initViews() {
        viewModel.getProducts()
    }
    
}