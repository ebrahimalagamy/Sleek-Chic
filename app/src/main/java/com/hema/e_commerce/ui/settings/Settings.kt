package com.hema.e_commerce.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.SettingsFragmentBinding
import com.hema.e_commerce.util.SharedPreferencesProvider

class Settings : Fragment() {

    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var binding: SettingsFragmentBinding

    var selectedItemIndex = 0
    private var arrayItems = arrayOf("EGP","USA","EUR")
    var selectedItem = arrayItems[selectedItemIndex]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())

        bindNav()
        bindUi()


    }

    private fun bindUi() {
        binding.tvWelcomeUser.text = sharedPref.getSettings().customer?.email ?: "empty"


        binding.btnCurrency.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Currency")
                .setSingleChoiceItems(arrayItems,selectedItemIndex){ _,which ->
                    selectedItemIndex=which
                    selectedItem =arrayItems[which]
                }
                .setPositiveButton("Ok"){ _,_ ->
                    Toast.makeText(requireActivity(),"$selectedItem Selected",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel"){ _,_ ->
                    // for cancel
                }.show()
        }
    }

    private fun bindNav() {
        binding.ivSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_signInFragment)
        }
        binding.ivSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_signUpFragment)
        }
        binding.btnWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_wishlist)
        }
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_profile)
        }
        binding.btnAddress.setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_address)
        }
//        binding.myAccountConstraint.visibility = View.GONE
    }

}