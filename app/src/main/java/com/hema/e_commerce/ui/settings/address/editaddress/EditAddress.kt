package com.hema.e_commerce.ui.settings.address.editaddress

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentEditAddressBinding
import com.hema.e_commerce.model.dataclass.customer.Customer
import com.hema.e_commerce.model.dataclass.customer.CustomerModel

class EditAddress : Fragment() {
    private lateinit var binding: FragmentEditAddressBinding
//    private lateinit var sharedPref: SharedPreferencesProvider

    val viewModel by lazy {
        EditAddressViewModel.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_address, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        bindNav()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindNav() {
        binding.tvCancel.setOnClickListener {
            findNavController().navigate(R.id.address)
        }
        binding.ibOpenMap.setOnClickListener {
            findNavController().navigate(R.id.action_editAddress_to_mapFragment)
        }
        binding.btnSaveAddressInfo.setOnClickListener {
            var address = binding.tvAddress.text.toString()
            var phone = binding.etPhoneNumber.text.toString()
            var name = binding.etName.text.toString()
            var id: Long? = viewModel.AuthRepo.sharedPref.getUserInfo().customer?.customerId

            val customer = CustomerModel(
                Customer(
                    firstName = name,
                    email = viewModel.AuthRepo.sharedPref.getUserInfo().customer?.email,
                    phone = phone
//                    addresses = address
                )
            )
            if (id != null) {
                viewModel.update(id, customer)
            }
            viewModel.updateUser.observe(viewLifecycleOwner) {
                if (it!!) {
                    Toast.makeText(requireContext(), "updated", Toast.LENGTH_LONG).show()
                } else Toast.makeText(requireContext(), "update failed", Toast.LENGTH_LONG).show()
            }

//            findNavController().navigate(R.id.address)
        }
    }

    private fun bindUI() {

        binding.etPhoneNumber.text = Editable.Factory.getInstance().newEditable(
            viewModel.AuthRepo.sharedPref.getUserInfo().customer?.phone ?: "Phone"
        )
//        binding.tvAddress.text =
//            (viewModel.AuthRepo.sharedPref.getUserInfo().customer?.addresses ?: "empty") as CharSequence?

        binding.etName.text = Editable.Factory.getInstance()
            .newEditable(viewModel.AuthRepo.sharedPref.getUserInfo().customer?.firstName ?: "Username")

    }


}