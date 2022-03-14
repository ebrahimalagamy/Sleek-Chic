package com.hema.e_commerce.ui.search

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.productList.TypeListAdapter
import com.hema.e_commerce.adapter.search.SearchAdapter
import com.hema.e_commerce.databinding.FragmentSearchBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.ListOfProductViewModelFactory
import com.hema.e_commerce.model.viewmodels.ListOfProductsViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    lateinit var viewModel: ListOfProductsViewModel
    lateinit var products: List<Product>
    var searchProducts: ArrayList<Product> = arrayListOf()
    private lateinit var seekBar:SeekBar
    private lateinit var price:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.searchView.isIconified = false

        val repository = Repository(RoomData(requireContext()))
        val listOfProductsViewModelProviderFactory =
            ListOfProductViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(
            this,
            listOfProductsViewModelProviderFactory
        )[ListOfProductsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandList()
        search()

//        click()
        filter()



    }


    //call all product
    fun brandList() {
        initViews()
        observeSearchProduct()
    }

    private fun observeSearchProduct() {
        viewModel.allProduct.observe(viewLifecycleOwner, Observer {
            products = it.products
            adapter = SearchAdapter(searchProducts)
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recListProduct.adapter = adapter
            binding.recListProduct.layoutManager = layoutManager

        })
    }

    private fun initViews() {
        viewModel.getProducts()
    }

    private fun search(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //win press on search not to crush so repeat the logic of text change
                searchProducts.clear()
                val searchText = query!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    products.forEach {
                        if (it.title.lowercase(Locale.getDefault()).contains(searchText)
                            || it.vendor.lowercase(Locale.getDefault()).contains(searchText)
                        ) {
                            searchProducts.add(it)
                        }
                    }
                    brandList()
                } else {
                    searchProducts.clear()
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //change when writing this the main logic
                searchProducts.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    products.forEach {
                        if (it.title.lowercase(Locale.getDefault()).contains(searchText)
                            || it.vendor.lowercase(Locale.getDefault()).contains(searchText)
                        ) {
                            searchProducts.add(it)
                        }
                    }
                    brandList()
                } else {
                    searchProducts.clear()
                }

                return false
            }

        })

    }
    fun filter(){
        binding.spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when {
                    parent!!.getItemAtPosition(position).equals("price 0 to 150") -> {
                        priceFilter("0","150")

                    }
                    parent!!.getItemAtPosition(position).equals("price 0 to 200") -> {
                        priceFilter("0","200")
                    }
                    parent!!.getItemAtPosition(position).equals("price 0 to 500") -> {
                        priceFilter("0","500")

                    }
                   /* parent!!.getItemAtPosition(position).equals("price 0 to 1000") -> {
                        priceFilter("0","1000")

                    }*/
                    parent!!.getItemAtPosition(position).equals("All prices") -> {
                        priceFilter("0","5000")

                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("yaaaaaaaa","NOTHING")
            }

        }
    }

    fun priceFilter(price1:String,price2: String){
        searchProducts.clear()
        products.forEach {
            if (it.variants[0].price in price1..price2) {
                searchProducts.add(it)
            }
        }
        //search()
        brandList()
    }

}



  /*
   //seek bar run put error on price logic
   private fun click() {
        binding.imageView3.setOnClickListener {
            binding.filterPrice.visibility = View.VISIBLE
        }
        binding.close.setOnClickListener {
            binding.filterPrice.visibility = View.GONE
            //Delete result
        }

    }

    private fun filter(){
        seekBar=binding.seekBar as SeekBar
        seekBar.progress = 15
        seekBar.max=2000

      //  seekBar.incrementProgressBy(10)


        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                price=progress.toString()
                binding.tvPrice.text=progress.toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                searchProducts.clear()
                products.forEach {
                    if (it.variants[0].price<=price) {
                        searchProducts.add(it)
                    }
                }
                brandList()
            }

        })


    }
*/


