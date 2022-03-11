package com.hema.e_commerce.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.CartFragmentBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.CartViewModelFactory
import com.hema.e_commerce.model.viewmodels.CartViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider

class Cart : Fragment(){
    lateinit var   cartAdapter: CartAdapter
    lateinit var cartFragmentBinding: CartFragmentBinding
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
        setupRecyclerView()

        viewModel.getCartProducts().observe(viewLifecycleOwner, Observer {product->
            //check if login or registration
            if (!SharedPreferencesProvider(requireContext()).getUserStatus()){
                cartFragmentBinding.layoutCartRec.visibility=View.VISIBLE
                cartFragmentBinding.notLoged.visibility=View.GONE

                cartAdapter.differ.submitList(product)
                cartFragmentBinding.textTotalprice.text=totalCalc(product).toString()
                cartFragmentBinding.tvSubTotal.text=totalCalc(product).toString()

                cartFragmentBinding.btCopoun.setOnClickListener {
                    val copoun = cartFragmentBinding.edtexCopoun.text.toString()
                    if (copoun == "hema5"){
                        val totalPricee =  cartFragmentBinding.textTotalprice.text.toString()
                        val discount = totalPricee.toDouble() - 20
                        cartFragmentBinding.textTotalprice.text = discount.toString()
                        cartFragmentBinding.tvDiscount.text = "-20"

                    }
                    else if(copoun != "hema5"){
                        cartFragmentBinding.tvSubTotal.text=totalCalc(product).toString()
                        cartFragmentBinding.tvDiscount.text = "0"
                    }
                }


            }else{
                cartFragmentBinding.notLoged.visibility=View.VISIBLE
                cartFragmentBinding.group.visibility=View.GONE

            }
        })

        bindButton()
    }

    private fun bindButton() {
        cartFragmentBinding.btCheckout.setOnClickListener {
            val totalPrice =cartFragmentBinding.textTotalprice.text.toString()
            if (totalPrice.isNotEmpty()){
                val action = CartDirections.actionCartToCheckout(totalPrice)
                Navigation.findNavController(requireView()).navigate(action)
            }else{
//                Toast.makeText()
            }
//            findNavController().navigate(R.id.action_cart_to_checkout)

        }
    }


    private fun totalCalc(items:List<CartProductData>):Double{
        var sumPrices :Double =0.0
        for (item in items){
            sumPrices+=item.price.toDouble()*item.count
        }
        return sumPrices
    }
    private fun setupRecyclerView(){

        cartAdapter = CartAdapter(viewModel)
        cartFragmentBinding.cartRec.apply {
            adapter=cartAdapter
            layoutManager= LinearLayoutManager(context)
        }
    }






}