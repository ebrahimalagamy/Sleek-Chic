package com.hema.e_commerce.ui.settings.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentAddressBinding
import com.hema.e_commerce.ui.settings.sharedpreferences.SharedPreferencesProvider


class Address : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private lateinit var sharedPref: SharedPreferencesProvider


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())
        binding.tvAddress.text = sharedPref.getUserInfo[0]
        binding.tvPhone.text = sharedPref.getUserInfo[1]
        binding.tvName.text = sharedPref.getUserInfo[2]

        bindUi()

    }

    private fun bindUi() {
        binding.tvEditAddres.setOnClickListener {
            findNavController().navigate(R.id.action_address_to_editAddress)
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_address_to_profile)
        }
    }


}