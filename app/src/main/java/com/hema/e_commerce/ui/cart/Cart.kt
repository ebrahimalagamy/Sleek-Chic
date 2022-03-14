package com.hema.e_commerce.ui.cart

import android.content.SharedPreferences
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
import com.hema.e_commerce.adapter.cart.CartAdapter
import com.hema.e_commerce.databinding.CartFragmentBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.viewModelFactory.CartViewModelFactory
import com.hema.e_commerce.model.viewmodels.CartViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider

class Cart : Fragment() {
    lateinit var cartAdapter: CartAdapter
    lateinit var cartFragmentBinding: CartFragmentBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var sharedPref: SharedPreferencesProvider
    lateinit var totalPriceWithoutSimp:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.cart_fragment, container, false)
        val repository = Repository(RoomData(requireContext()))
        val cartViewModelProviderFactory =
            CartViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, cartViewModelProviderFactory)[CartViewModel::class.java]

        return cartFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())

        setupRecyclerView()

        viewModel.getCartProducts().observe(viewLifecycleOwner) { product ->
            if (product.isEmpty()) {
                cartFragmentBinding.imageView5.visibility = View.VISIBLE
                cartFragmentBinding.cslayout.visibility = View.GONE

            } else {
                cartFragmentBinding.imageView5.visibility = View.GONE
                cartFragmentBinding.cslayout.visibility = View.VISIBLE
            }
            //check if login or registration
            if (sharedPref.isSignIn) {
                cartFragmentBinding.layoutCartRec.visibility = View.VISIBLE
                cartFragmentBinding.notLoged.visibility = View.GONE
                cartAdapter.differ.submitList(product)

                val sharedPreferences: SharedPreferences =
                    requireContext().getSharedPreferences("currency", 0)
                when (sharedPreferences.getString("currency", "EGP")) {
                    "EGP" -> {
                        cartFragmentBinding.textTotalprice.text =
                            totalCalc(product).toString() + " " + getString(R.string.eg)

                        cartFragmentBinding.tvSubTotal.text =
                            totalCalc(product).toString() + " " + getString(R.string.eg)

                        totalPriceWithoutSimp = totalCalc(product).toString()

                    }
                    "USA" -> {
                        val usCurrancy = ((totalCalc(product).toString()).toDouble() / (15.71))
                        val number: Double = String.format("%.2f", usCurrancy).toDouble()
                        cartFragmentBinding.textTotalprice.text =
                            number.toString() + " " + getString(R.string.us)
                        //
                        val usCurrancySub = ((totalCalc(product).toString()).toDouble() / (15.71))
                        val numberSub: Double = String.format("%.2f", usCurrancySub).toDouble()
                        cartFragmentBinding.tvSubTotal.text =
                            numberSub.toString() + " " + getString(R.string.us)

                        totalPriceWithoutSimp = number.toString()


                    }
                    "EUR" -> {
                        val ureCurrancy = ((totalCalc(product).toString()).toDouble() / (17.10))
                        val number: Double = String.format("%.2f", ureCurrancy).toDouble()
                        cartFragmentBinding.textTotalprice.text =
                            number.toString() + " " + getString(R.string.eur)
                        //

                        val ureCurrancySub = ((totalCalc(product).toString()).toDouble() / (17.10))
                        val numberSub: Double = String.format("%.2f", ureCurrancySub).toDouble()
                        cartFragmentBinding.tvSubTotal.text =
                            numberSub.toString() + " " + getString(R.string.eur)
                        totalPriceWithoutSimp = number.toString()

                    }
                    else -> {
                        cartFragmentBinding.textTotalprice.text =
                            totalCalc(product).toString() + " " + getString(R.string.eg)
                        cartFragmentBinding.tvSubTotal.text =
                            totalCalc(product).toString() + " " + getString(R.string.eg)

                        totalPriceWithoutSimp = totalCalc(product).toString()


                    }
                }

                cartFragmentBinding.btCopoun.setOnClickListener {
                    val copoun = cartFragmentBinding.edtexCopoun.text.toString()
                    if (copoun == "hema5") {
                        val totalPricee = totalPriceWithoutSimp
                        val discount = totalPricee.toDouble() * 90 / 100
                        cartFragmentBinding.textTotalprice.text = discount.toString()
                        cartFragmentBinding.tvDiscount.text = "% 10"
                        totalPriceWithoutSimp = discount.toString()

                    } else if (copoun != "hema5") {
                        cartFragmentBinding.tvSubTotal.text = totalPriceWithoutSimp
                        cartFragmentBinding.tvDiscount.text = "0"
                    }
                }
                cartFragmentBinding.btCheckout.setOnClickListener {
                    val totalPrice = totalPriceWithoutSimp
                    if (totalPrice.isNotEmpty()) {
                        val action = CartDirections.actionCartToCheckout(totalPrice)
                        Navigation.findNavController(requireView()).navigate(action)
                    }

                }

            } else {
                cartFragmentBinding.notLoged.visibility = View.VISIBLE
                cartFragmentBinding.layoutCartRec.visibility = View.GONE
                cartFragmentBinding.btCheckout.visibility = View.GONE

            }
        }
    }

    private fun totalCalc(items: List<CartProductData>): Double {
        var sumPrices = 0.0
        for (item in items) {
            sumPrices += item.price.toDouble() * item.count
        }
        return sumPrices
    }
    private fun setupRecyclerView() {

        cartAdapter = CartAdapter(viewModel,requireContext())
        cartFragmentBinding.cartRec.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}