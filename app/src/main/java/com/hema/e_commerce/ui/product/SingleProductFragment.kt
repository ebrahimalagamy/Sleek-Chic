package com.hema.e_commerce.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.HomeFragmentBinding

class SingleProductFragment : Fragment(R.layout.single_product_fragment) {

    private lateinit var viewModel: SingleProductViewModel
   private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.single_product_fragment, container, false)
        return binding.root
    }



}