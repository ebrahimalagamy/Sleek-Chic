package com.hema.e_commerce.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentTypeListProductBinding

class SearchFragment : Fragment() {
    lateinit var binding: FragmentTypeListProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_type_list_product, container, false)
        binding.searchView.isIconified = false
        return binding.root
    }
    
}