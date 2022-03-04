package com.hema.e_commerce.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.SingleProductFragmentBinding
import com.hema.e_commerce.model.viewmodels.SingleProductViewModel

class SingleProductFragment : Fragment(R.layout.single_product_fragment) {

    private lateinit var viewModel: SingleProductViewModel
    private lateinit var binding: SingleProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.single_product_fragment, container, false)
        return binding.root
    }


}