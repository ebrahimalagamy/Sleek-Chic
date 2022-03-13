package com.hema.e_commerce.ui.settings.address.addaddress

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.databinding.FragmentAddAddressBinding

class AddAddressFragment : Fragment() {

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
        binding.btnSave.setOnClickListener {
            viewModel.addAddress()
            viewModel.addressAdded.observe(requireActivity()) {
                if (it) {
                    viewModel.addressAdded.postValue(false)
                    findNavController().popBackStack()

                }
            }
        }

//        binding.ibOpenMap.setOnClickListener {
//            val action = EditAddressDirections.actionEditAddressToMapFragment(addressItem)
//            findNavController().navigate(action)
//        }

    }

}