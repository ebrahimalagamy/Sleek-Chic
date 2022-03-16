package com.hema.e_commerce.ui.typelistofproduct

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.home.WishListNotificationAdapter
import com.hema.e_commerce.adapter.productList.TypeListAdapter
import com.hema.e_commerce.databinding.FragmentTypeListProductBinding
import com.hema.e_commerce.model.dataclass.allProducts.Product
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.ListOfProductViewModelFactory
import com.hema.e_commerce.model.viewmodels.ListOfProductsViewModel
import com.hema.e_commerce.ui.progresspar.ProgressBarSetting
import com.hema.e_commerce.util.Constant.BRAND_KEY
import com.hema.e_commerce.util.Constant.FLAG
import com.hema.e_commerce.util.Constant.SUB_COLLECTION_ID
import com.hema.e_commerce.util.Constant.SUB_COLLECTION_NAME
import com.hema.e_commerce.util.SharedPreferencesProvider


class TypeListProductsFragment : Fragment() {
    lateinit var binding: FragmentTypeListProductBinding
    lateinit var adapter: TypeListAdapter
    lateinit var viewModel:ListOfProductsViewModel
     var collectionsId:Long =398033617127
    lateinit var subCollectionsName:String
    lateinit var brandName:String
    private lateinit var sharedPref: SharedPreferencesProvider


    lateinit  var productList:List<Product>
    lateinit   var list: MutableList<Product>
       var filterlist: MutableList<Product> = mutableListOf()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_type_list_product, container, false)
        val repository= Repository(RoomData(requireContext()))
        val listOfProductsViewModelProviderFactory =
            ListOfProductViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this, listOfProductsViewModelProviderFactory)[ListOfProductsViewModel::class.java]



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())


        ProgressBarSetting().setProgress(requireActivity())
        val flag = arguments?.get(FLAG)
        Log.d("flaaaag","string $flag")
        if (flag==0){
            catagoryList()

        }else if(flag == 1){
            BrandList()
        }
        iconBadges()
        back()



    }
    ////////////////////////////////////////////////////////////
//Aya


fun catagoryList(){
    subCollectionsName = arguments?.getString(SUB_COLLECTION_NAME).toString()
    collectionsId= arguments?.getLong(SUB_COLLECTION_ID)!!
    initViews(collectionsId)
    observe()
}
    fun observe() {
        viewModel.SubCollectionsProductsLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
           productList = it.products
             list = mutableListOf()
            for(i in productList){
                if(i.product_type.equals(subCollectionsName)){
                    list.add(i)
                }
            }
            if(list.size==0){

                binding.tvEmpty.text=getString(R.string.emptylist)
            }
            adapter = TypeListAdapter(list,viewModel)
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recListProduct.adapter = adapter
            binding.recListProduct.layoutManager = layoutManager

        })
      //  filter()

    }

    private fun initViews(idSubCollections:Long) {
        viewModel.getSubCollectionsProducts(idSubCollections)
    }
////////////////////////////////////////////////////////////////////////////////////
    //Mohamed
fun BrandList(){
    brandName = arguments?.getString(BRAND_KEY).toString()
    initViews()
    observeBrandProduct()
}
    fun observeBrandProduct() {
        viewModel.allProduct .observe(viewLifecycleOwner, Observer {
             productList = it.products

             list= mutableListOf()

            for(i in productList){
                if(i.vendor.equals(brandName)){
                    list.add(i)
                }
            }
            adapter = TypeListAdapter(list,viewModel)
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recListProduct.adapter = adapter
            binding.recListProduct.layoutManager = layoutManager

        })
    //  filter()

    }

    private fun initViews() {
        viewModel.getProducts()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun iconBadges() {
        val wishListIcon = WishListNotificationAdapter(binding.favourite)
        if (sharedPref.isSignIn) {
            viewModel.getFavProducts(sharedPref.getUserInfo().customer?.customerId!!)
                .observe(viewLifecycleOwner) {
                    wishListIcon.updateView(it.size)
                }
        } else {
            wishListIcon.hideNumber()
        }

        wishListIcon.Button.setOnClickListener {
            if (sharedPref.isSignIn) {
                findNavController().navigate(R.id.wishlist)
            } else {
                Snackbar.make(requireView(), R.string.sign_in_message, Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Sign In") {
                            findNavController().navigate(R.id.Settings)
                        }
                        show()
                    }
            }
        }
    }
    fun back(){
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

//////////////////////////////////////////////////////////
   /* fun filter(){
    binding.spinner2.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            filterlist.clear()
            if (parent!!.getItemAtPosition(position).equals("ByName")){
                filterlist = list.sortedBy { it.title }.toMutableList()
            }else if (parent!!.getItemAtPosition(position).equals("price: Low to High")){
                filterlist = list.sortedBy { it.variants!![0].price}.toMutableList()
            }else if(parent!!.getItemAtPosition(position).equals("price: High to Low")){
                filterlist = list.sortedByDescending { it.variants!![0].price }.toMutableList()
            }
            binding.recListProduct.adapter=TypeListAdapter(filterlist)

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Log.d("yaaaaaaaa","NOTHING")
        }

    }
    }*/

}