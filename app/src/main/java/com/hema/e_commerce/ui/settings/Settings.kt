package com.hema.e_commerce.ui.settings

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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
    private var arrayItems = arrayOf("EGP", "USA", "EUR")
    var selectedItem = arrayItems[selectedItemIndex]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireActivity())

        bindNav()
        bindUi()
        bindSignIn()


    }

    private fun bindSignIn() {
        if (sharedPref.isSignIn){
            binding.tvMyAccount.visibility = View.VISIBLE
            binding.myLinear.visibility = View.VISIBLE
            binding.btnSignOut.visibility = View.VISIBLE
            binding.constraintLayoutForAuth.visibility = View.GONE
        }
        else{
            binding.tvMyAccount.visibility = View.GONE
            binding.myLinear.visibility = View.GONE
            binding.btnSignOut.visibility = View.GONE
            binding.constraintLayoutForAuth.visibility = View.VISIBLE

        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun bindUi() {
        if (sharedPref.isSignIn){
        binding.tvWelcomeUser.text =
            sharedPref.getUserInfo().customer?.email ?: ""
        binding.tvUserName.text = sharedPref.getUserInfo().customer?.firstName ?: ""
        }else{
            binding.tvWelcomeUser.text = "Register now to enjoy shopping !"
        }

        binding.btnCurrency.setOnClickListener {

            val sharedPreferences: SharedPreferences =
                requireContext().getSharedPreferences("currency", 0)
            selectedItemIndex = sharedPreferences.getInt("currencyindex", 0)

            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Currency")
                .setSingleChoiceItems(arrayItems, selectedItemIndex) { _, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItems[which]
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("currency", selectedItem)
                    editor.putInt("currencyindex", selectedItemIndex)
                    editor.apply()
                    editor.commit()


                }
                .setPositiveButton("Ok") { _, _ ->
                    Toast.makeText(requireActivity(), "$selectedItem Selected", Toast.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    // for cancel
                }.show()
        }
        binding.btnSignOut.setOnClickListener {
            requireActivity().deleteSharedPreferences("myPref")
            findNavController().navigate(R.id.Settings)
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

        binding.btnOrder.setOnClickListener {
            findNavController().navigate(R.id.action_Settings_to_orderFragment)
        }

    }

}