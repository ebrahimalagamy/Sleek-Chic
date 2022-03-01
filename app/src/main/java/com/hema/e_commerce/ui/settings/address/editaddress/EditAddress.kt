package com.hema.e_commerce.ui.settings.address.editaddress

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        binding.tvAddress.text = sharedPref.getLocation[2]
        binding.etPhoneNumber.text =
            Editable.Factory.getInstance().newEditable(sharedPref.getUserInfo[0])
        binding.etName.text = Editable.Factory.getInstance().newEditable(sharedPref.getUserInfo[1])



        binding.tvCancel.setOnClickListener {
            findNavController().navigate(R.id.action_editAddress_to_address)
        }
        binding.ibOpenMap.setOnClickListener {
            findNavController().navigate(R.id.action_editAddress_to_mapFragment)
        }
        binding.btnSaveAddressInfo.setOnClickListener {
//            val address = binding.tvAddress.text.toString()
            val phone = binding.etPhoneNumber.text.toString()
            val name = binding.etName.text.toString()
            sharedPref.setUserInfo(phone, name)
            findNavController().navigate(R.id.action_editAddress_to_address)
        }


    }


}