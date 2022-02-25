package com.hema.e_commerce.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.ProfileFragmentBinding

class Profile : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()

    }

    private fun bindUi() {
        binding.ivSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_signInFragment)
        }
        binding.ivSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_signUpFragment)
        }
        binding.btnWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_wishlist)
        }
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_editProfile)
        }
        binding.btnAddress.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_address)
        }
    }

}