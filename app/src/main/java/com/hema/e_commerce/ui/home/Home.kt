package com.hema.e_commerce.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.home.BrandAdapter
import com.hema.e_commerce.adapter.home.ProductsAdapter
import com.hema.e_commerce.databinding.HomeFragmentBinding


class Home : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var productAdapter: ProductsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        imageSlider()
        initViews()
        obesrvers()



    }


    fun obesrvers(){
        observeBrand()
       // observeSaleProduct()
    }

    fun observeBrand() {
        viewModel.brandsLiveData.observe(viewLifecycleOwner, Observer {
            var brandList = it.smart_collections
            brandAdapter = BrandAdapter(arrayListOf())
            brandAdapter.updateBrand(brandList)
            binding.brandsRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            binding.brandsRecycler.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL,false)


            binding.brandsRecycler.adapter = brandAdapter
        })
    }
//    fun observeSaleProduct() {
//        viewModel.onSaleProducts .observe(viewLifecycleOwner, Observer {
//            var productList=it.products
//            productAdapter= ProductsAdapter(arrayListOf())
//            productAdapter.updateproduct(productList)
//            binding.bestSellingRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
//            binding.bestSellingRecyclerView.adapter=productAdapter
//        })
//    }


     private fun initViews() {
         viewModel.getBrand()
      //   viewModel.getOnHomeProducts()
         viewModel.getOnSaleProducts()
    }


    private fun imageSlider() {
        //function for image slider

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.cubon1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubon3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubon4, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.nike1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubonkids, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubontshirt, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.shoes2, ScaleTypes.FIT))


        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)




        binding.imageSlider.setImageList(imageList)
    }
}