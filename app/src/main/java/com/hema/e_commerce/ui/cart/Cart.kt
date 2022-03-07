package com.hema.e_commerce.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.cart.CartAdapter
import com.hema.e_commerce.databinding.CartFragmentBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.cartroom.RoomData
import com.hema.e_commerce.model.viewModelFactory.CartViewModelFactory
import com.hema.e_commerce.model.viewModelFactory.HomeViewModelFactory
import com.hema.e_commerce.model.viewmodels.CartViewModel
import com.hema.e_commerce.model.viewmodels.HomeViewModel

class Cart : Fragment() {

   lateinit var   cartAdapter: CartAdapter
    lateinit var cartFragmentBinding: CartFragmentBinding
    lateinit var arrayCart: ArrayList<CartProductData>
    private var customerID = ""
    private var totalPrice = 0.0
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.cart_fragment, container, false)
        val repository= Repository(RoomData(requireContext()))
        val cartViewModelProviderFactory = CartViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this,cartViewModelProviderFactory)[CartViewModel::class.java]

        return cartFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(arrayListOf())
        cartFragmentBinding.cartRec.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=cartAdapter
        }
/*        viewModel.getAllCartList().observe(viewLifecycleOwner,{
            cartFragmentBinding.cartGroup.visibility=View.VISIBLE
            var products=it.toMutableList()
            cartAdapter.addNewList(products)
        })*/


    }

}