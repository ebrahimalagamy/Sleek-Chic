package com.hema.e_commerce.ui.settings.profile.editprofile

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentEditProfileBinding
import com.hema.e_commerce.model.dataclass.customer.Customer
import com.hema.e_commerce.model.dataclass.customer.CustomerModel


class EditProfile : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

    private val viewModel by lazy {
        EditProfileViewModel.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
        bindNav()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindNav() {
        binding.btnSaveProfileInfo.setOnClickListener {
            var name = binding.etUsername.text.toString()
            var id: Long? = viewModel.AuthRepo.sharedPref.getUserInfo().customer?.customerId

            val customer = CustomerModel(
                Customer(
                    firstName = name,
                    email = viewModel.AuthRepo.sharedPref.getUserInfo().customer?.email
                )
            )
            if (id != null) {
                viewModel.update(id, customer)
            }
            viewModel.updateUser.observe(viewLifecycleOwner) {
                if (it!!) {
                    Toast.makeText(requireContext(), "updated", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.address)
                } else Toast.makeText(requireContext(), "update failed", Toast.LENGTH_LONG).show()
            }
        }
        binding.tvCancel.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
    private fun bindUi() {
        binding.etUsername.text = Editable.Factory.getInstance()
            .newEditable(viewModel.AuthRepo.sharedPref.getUserInfo().customer?.firstName ?: "Username")
    }


}