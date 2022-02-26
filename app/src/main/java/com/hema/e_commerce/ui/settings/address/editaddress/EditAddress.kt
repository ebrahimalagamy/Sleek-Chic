package com.hema.e_commerce.ui.settings.address.editaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentEditAddressBinding
import com.hema.e_commerce.ui.settings.sharedpreferences.SharedPreferencesProvider


class EditAddress : Fragment() {
    private lateinit var binding: FragmentEditAddressBinding
    private lateinit var sharedPref: SharedPreferencesProvider


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_address, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())
        binding.tvAddress.text = sharedPref.latLong[2]


    }




}