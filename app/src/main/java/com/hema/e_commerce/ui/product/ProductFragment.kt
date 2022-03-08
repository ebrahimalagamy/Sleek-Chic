package com.hema.e_commerce.ui.product

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
    lateinit var navController: NavController

    private lateinit var viewModel: SingleProductViewModel
    lateinit var binding:FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        val repository= Repository(RoomData(requireContext()))
        val singleProductsViewModelProviderFactory =
            SingleProductViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this, singleProductsViewModelProviderFactory)[SingleProductViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId= arguments?.getLong(PRODUCT)

        if (productId!=null){
            initViews(productId)
            observe()
        }
    }

    fun observe() {
        viewModel.singleProductLiveData.observe(viewLifecycleOwner, Observer {

            var adapter:PagerAdapter= ProductAdapter(requireContext(),it.product.images)
            var product=it.product
            binding.viewPager.adapter = adapter
            it.product.handle
            Log.i("n", "observe: "+  it.product.handle)
            binding.tvDescProduct.text=  it.product.body_html

            val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("currency", 0)
            var value = sharedPreferences.getString("currency","EGP")
            when(value){
                "EGP"->  binding.tvPrice.text=it.product.variants.get(0).price+" "+getString(R.string.eg)
                "USA"-> {
                    var usCurrancy=   ((it.product.variants.get(0).price).toDouble() / (15.71))
                    val number:Double=String.format("%.2f",usCurrancy).toDouble()
                    binding.tvPrice.text = number.toString() + " " + getString(R.string.us)

                }
                "EUR"->      {
                    var ureCurrancy=   ((it.product.variants.get(0).price).toDouble() / (17.10))
                    val number:Double=String.format("%.2f",ureCurrancy).toDouble()
                    binding.tvPrice.text=number.toString()+" "+getString(R.string.eur)
                }
                else->  binding.tvPrice.text=it.product.variants.get(0).price+" "+getString(R.string.eg)

            }
            binding.tvTitle.text=it.product.title

            binding.addToCart.setOnClickListener {
                //befor that we will check if user login or not
                val cartitem= CartProductData(
                  product.id,product.image.src,product.title,product.variants.get(0).price+" "+"EG",product.variants.get(0).inventory_quantity
                )
                viewModel.saveCartList(cartitem)
            }


        })


        reviewAction()
    }


    fun reviewAction(){
        navController = Navigation.findNavController(binding.root)

        binding.tvReviews.setOnClickListener{

            navController.navigate(R.id.action_productFragment_to_reviewFragment)



        }



    }

    private fun initViews(productId:Long) {
        viewModel.getSingleProduct(productId)
    }




    fun setFavouriteAction(){




    }


    fun setRating() {

    }









}