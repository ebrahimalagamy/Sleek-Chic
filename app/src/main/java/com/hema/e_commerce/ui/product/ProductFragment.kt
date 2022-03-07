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
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.cartroom.RoomData
import com.hema.e_commerce.model.viewModelFactory.SingleProductViewModelFactory
import com.hema.e_commerce.model.viewmodels.SingleProductViewModel
import com.hema.e_commerce.util.Constant.PRODUCT


class ProductFragment : Fragment() {

    private lateinit var viewModel: SingleProductViewModel
    lateinit var binding:FragmentProductBinding
    private var optionsSelected: String? = null
    var num = 1
    //lateinit var product:Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        val repository= Repository(RoomData(requireContext()))
        val singleProductsViewModelProviderFactory =
            SingleProductViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this, singleProductsViewModelProviderFactory)[SingleProductViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId= arguments?.getLong(PRODUCT)!!
        Log.i("idarg", "onViewCreated: "+productId)

    initViews(productId)

        observe()
    }

    fun observe() {

        viewModel.singleProductLiveData.observe(viewLifecycleOwner, Observer {
            var adapter:PagerAdapter= ProductAdapter(requireContext(),it.product.images)
            var product=it.product
            binding.viewPager.adapter = adapter

            it.product.handle
            Log.i("n", "observe: "+  it.product.handle)
            binding.tvDescProduct.text=  it.product.body_html
            binding.tvPrice.text=it.product.variants.get(0).price+" "+getString(R.string.eg)
            binding.tvTitle.text=it.product.title

            binding.addToCart.setOnClickListener {
//befor that we will check if user login or not

               val cartitem= CartProductData(
                   product.id,product.title,product.image.toString(),product.variants.toString()
                )

               // RoomData.getDatabase(requireContext()).getLocalDataObject().saveAllCartList(cartitem)
                  // Toast.makeText(this.context,"its add to cart table",Toast.LENGTH_LONG)


            }

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