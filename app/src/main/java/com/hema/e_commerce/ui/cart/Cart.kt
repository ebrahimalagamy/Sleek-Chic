package com.hema.e_commerce.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.CartFragmentBinding
<<<<<<< Updated upstream
import com.hema.e_commerce.ui.category.repository.Repository

class Cart : Fragment() {

lateinit var cartAdapter: CartAdapter
lateinit var cartFragmentBinding: CartFragmentBinding
lateinit var arrayCart:ArrayList<CartData>
=======
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData

class Cart : Fragment() {

    lateinit var cartAdapter: CartAdapter
    lateinit var cartFragmentBinding: CartFragmentBinding
    lateinit var arrayCart: ArrayList<CartProductData>


    private var customerID = ""
    private var totalPrice = 0.0

>>>>>>> Stashed changes


    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

<<<<<<< Updated upstream
        cartFragmentBinding=DataBindingUtil.inflate(inflater,R.layout.cart_fragment,container,false)
       // cartFragmentBinding.btCheckout.setOnClickListener {  }
      //  cartFragmentBinding.btCopoun.setOnClickListener {  }
    //    cartFragmentBinding.edtexCopoun.setOnClickListener {  }
     //   cartFragmentBinding.textTotalprice.text=

    return cartFragmentBinding.root
=======
        cartFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.cart_fragment, container, false)
        viewModel =
            ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).
            get(CartViewModel::class.java)
        return cartFragmentBinding.root


>>>>>>> Stashed changes
    }

 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        var fragMana: FragmentManager? = fragmentManager
        arrayCart=Repository().arrayList
        cartAdapter= CartAdapter(fragMana,arrayCart,requireContext())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        cartFragmentBinding.cartRec.adapter=cartAdapter
        cartFragmentBinding.cartRec.layoutManager=layoutManager

    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartAdapter = CartAdapter(arrayListOf(),viewModel)
        cartFragmentBinding.cartRec.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=cartAdapter
        }

    }


}