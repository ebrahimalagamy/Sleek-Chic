package com.hema.e_commerce.ui.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

import android.content.res.Configuration

import android.os.Build

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate

import androidx.annotation.RequiresApi

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.SettingsFragmentBinding
import com.hema.e_commerce.util.SharedPreferencesProvider
import java.util.*

class Settings : Fragment() {

    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var binding: SettingsFragmentBinding

    var selectedItemIndex = 0

    var selectedLanguageIndex = -1

    private var arrayItems = arrayOf("EGP","USA","EUR")

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

        binding.tvWelcomeUser.text = sharedPref.getUserInfo().customer?.email ?: "Email"
        binding.tvUserName.text = sharedPref.getUserInfo().customer?.firstName ?: "User name"
        binding.btnLanguage.setOnClickListener{
            showChangeLang()

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

//
    private fun showChangeLang() {

        val listItmes = arrayOf("عربي",  "English")
    val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("language", 0)

    selectedLanguageIndex = sharedPreferences.getInt("languageindex",-1)
    Log.i("setting language", "showChangeLang: "+selectedLanguageIndex)


        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle(getString(R.string.chooselanguage))
        mBuilder.setSingleChoiceItems(listItmes, selectedLanguageIndex) { dialog, which ->
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("languageindex", which)
            editor.apply()
            editor.commit()

            if (which == 0) {
                setLocate("ar")
                requireActivity().recreate()

            } else if (which == 1) {
                setLocate("en")
                requireActivity().recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

//        val editor = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
//        editor.putString("My_Lang", Lang)
//        editor.apply()
    }

//    fun loadLocate() {
//        val sharedPreferences = requireActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE)
//        val language = sharedPreferences.getString("My_Lang", "")
//        setLocate(language!!)
//    }



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