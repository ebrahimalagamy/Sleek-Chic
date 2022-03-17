package com.hema.e_commerce.ui.settings.address.addaddress

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentAddAddressBinding
import com.hema.e_commerce.model.dataclass.customer.Address
import com.hema.e_commerce.model.dataclass.customer.AddressModel
import kotlin.random.Random

class AddAddressFragment : Fragment() {
    private lateinit var addressLine: String
    private lateinit var city: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var phone: String
    private lateinit var isDefult: String
    lateinit var addressAdded: String

    val binding by lazy {
        FragmentAddAddressBinding.inflate(layoutInflater)
    }

    val viewModel by lazy {
        AddAddressViewModel.create(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.vm = viewModel
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        bindUI()
        bindNav()


//        binding.btnSave.setOnClickListener {
//            viewModel.addAddress()
//            viewModel.addressAdded.observe(requireActivity()) {
//                if (it) {
//                    viewModel.addressAdded.postValue(false)
//                    findNavController().popBackStack()
//
//                }
//            }
//        }

//        binding.ibOpenMap.setOnClickListener {
//            val action = EditAddressDirections.actionEditAddressToMapFragment(addressItem)
//            findNavController().navigate(action)
//        }

    }

    private fun bindNav() {
//        binding.ivMap.setOnClickListener {
//            findNavController().navigate(R.id.action_addAddressFragment_to_mapFragment)
//        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindUI() {
        binding.btnSave.setOnClickListener {
            if (validateForm()) {

                val address = AddressModel(
                    Address(
                        id = Random.nextLong(1, 1000000),
                        address = addressLine,
//                        city = city,
                        firstName = firstName,
                        lastName = lastName,
                        zip = "000",
                        default = isDefult.toBoolean(), phone = phone
                    )
                )
                viewModel.postData(address)
                viewModel.add.observe(viewLifecycleOwner) {
                    if (it == true) {
                        viewModel.add.postValue(false)
                        Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    } else
                        Toast.makeText(requireContext(), "Try again", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        addressLine = binding.etAddressLine.text.toString()
//        city = binding.etCity.text.toString()
        firstName = binding.etFirstName.text.toString()
        lastName = binding.etLastName.text.toString()
        phone = binding.etPhone.text.toString()
        isDefult = binding.rbSetAsDefault.text.toString()
        return true
    }


}