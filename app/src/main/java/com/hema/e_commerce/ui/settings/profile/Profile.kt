package com.hema.e_commerce.ui.settings.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentProfileBinding
import com.hema.e_commerce.util.SharedPreferencesProvider


class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: SharedPreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())
        bindUi()
        bindNav()

    }

    private fun bindNav() {
        binding.tvEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_editProfile)
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.Settings)
        }
    }

    private fun bindUi() {
        binding.email.text = sharedPref.getUserInfo().customer?.email ?: "email@gmail.com"
        binding.tvUsername.text = sharedPref.getUserInfo().customer?.firstName ?: "Username"

    }
}