package com.hema.e_commerce.ui.settings.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentCheckoutBinding

class Checkout : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private val args:CheckoutArgs by navArgs()
//    private lateinit var config: Paypal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
    }

    private fun bindUI() {
        val totalPrice = args.totalPrice
        binding.tvTotalPrice.text = "EGP $totalPrice"
        binding.tvSubtotal.text = "EGP $totalPrice"

    }


}