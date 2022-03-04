package com.hema.e_commerce.ui.product.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentProductBinding
import com.hema.e_commerce.model.viewmodels.SingleProductViewModel
import com.hema.e_commerce.model.viewmodels.SubCollectionViewModel


class ProductFragment : Fragment() {

    private lateinit var viewModel: SingleProductViewModel
    lateinit var binding:FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        viewModel = ViewModelProvider(this).get(SingleProductViewModel::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId= arguments?.getLong("productId")!!
        Log.i("idarg", "onViewCreated: "+productId)
    initViews(productId)
        observe()
//        var categoriesList = arrayListOf<Int>(R.drawable.accessorieskids2,R.drawable.accessoriesman,R.drawable.accessorieswoman)
//         var adapter:PagerAdapter=ProductAdapter(requireContext(),categoriesList)
//
//        binding.viewPager.adapter = adapter


    }

    fun observe() {
        viewModel.singleProductLiveData.observe(viewLifecycleOwner, Observer {

           // var adapter:PagerAdapter=ProductAdapter(requireContext(),it.images)

           // binding.viewPager.adapter = adapter
            Log.i("n", "observe: "+it.handle)
            binding.tvDescProduct.text=it.handle
            //binding.tvPrice.text=it.variants.get(0).price


        })
    }

    private fun initViews(productId:Long) {
        viewModel.getSingleProduct(productId)
    }




    fun setFavouriteAction(){




 }


fun setRating() {

}









}