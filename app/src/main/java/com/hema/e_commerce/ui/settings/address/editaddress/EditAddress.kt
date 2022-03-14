package com.hema.e_commerce.ui.settings.address.editaddress

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentEditAddressBinding
import com.hema.e_commerce.model.dataclass.customer.Address
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import com.hema.e_commerce.model.dataclass.customer.AddressesItem
import com.hema.e_commerce.util.SharedPreferencesProvider

class EditAddress : Fragment() {
    private lateinit var binding: FragmentEditAddressBinding
    private val args: EditAddressArgs by navArgs()
    lateinit var addressItem: AddressesItem
    lateinit var sharedPref: SharedPreferencesProvider

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
        sharedPref = SharedPreferencesProvider(requireContext())
        addressItem = args.adress
        bindUI()
        bindNav()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindNav() {
        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ibOpenMap.setOnClickListener {
            val action = EditAddressDirections.actionEditAddressToMapFragment(addressItem)
            findNavController().navigate(action)
        }

        binding.btnSaveAddressInfo.setOnClickListener {
            var address = addressItem.address1
            var phone = binding.etPhoneNumber.text.toString()
            var name = binding.etName.text.toString()
            var split = name.split(" ")
            var id: Long? = viewModel.AuthRepo.sharedPref.getUserInfo().customer?.customerId
            val addressModel = AddressModel(
                Address(
                    id = addressItem.id,
                    address = address,
                    phone = phone,
                    firstName = split[0],
                    lastName = split[1]
                )
            )
            viewModel.updateAddress(addressModel)
        }
        viewModel.updateUser.observe(viewLifecycleOwner) {
            if (it!!) {
                Toast.makeText(requireContext(), "updated", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
                viewModel.updateUser.postValue(false)
            } else Toast.makeText(requireContext(), "update failed", Toast.LENGTH_LONG).show()
        }
    }


    private fun bindUI() {

        binding.etPhoneNumber.text = Editable.Factory.getInstance().newEditable(addressItem.phone)
        binding.tvAddress.text = addressItem.address1
        binding.etName.text = Editable.Factory.getInstance()
            .newEditable(addressItem.firstName + " " + addressItem.lastName)

    }


}