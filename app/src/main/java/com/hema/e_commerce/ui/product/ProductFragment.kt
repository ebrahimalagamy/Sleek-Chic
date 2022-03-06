package com.hema.e_commerce.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.singleProduct.ProductAdapter
import com.hema.e_commerce.databinding.FragmentProductBinding
import com.hema.e_commerce.model.viewmodels.SingleProductViewModel


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
    }

    fun observe() {
        viewModel.singleProductLiveData.observe(viewLifecycleOwner, Observer {

            var adapter:PagerAdapter= ProductAdapter(requireContext(),it.product.images)

            binding.viewPager.adapter = adapter
            it.product.handle
            Log.i("n", "observe: "+  it.product.handle)
            binding.tvDescProduct.text=  it.product.body_html
            binding.tvPrice.text=it.product.variants.get(0).price+" "+getString(R.string.eg)
            binding.tvTitle.text=it.product.title


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