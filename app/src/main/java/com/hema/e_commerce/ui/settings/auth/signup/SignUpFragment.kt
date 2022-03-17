package com.hema.e_commerce.ui.settings.auth.signup

import android.os.Build
import android.os.Bundle
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
import com.hema.e_commerce.databinding.FragmentSignUpBinding
import com.hema.e_commerce.model.dataclass.customer.Address
import com.hema.e_commerce.model.dataclass.customer.Customer
import com.hema.e_commerce.model.dataclass.customer.CustomerModel
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlin.random.Random

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firstName: String
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var userConfirmPassword: String
    lateinit var sharedPref: SharedPreferencesProvider
    var lineItem: MutableList<Address> = arrayListOf()

//    private lateinit var phone: String

    private val viewModel by lazy {
        RegisterViewModel.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreferencesProvider(requireContext())

        bindUi()
        bindNav()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindNav() {
//        val id = Random.nextLong(1, 1000000)
//        val address = sharedPref.getLocation[2]
//        val lastName = ""
//        val defult: Boolean = true
        binding.btnSignUp.setOnClickListener {
            if (validateForm()) {

                val customer = CustomerModel(
                    Customer(
                        firstName = firstName,
                        lastName = userPassword,
                        email = userEmail,
                        password = userPassword,
                        passwordConfirmation = userConfirmPassword,
//                        addresses = lineItem
//                        phone = phone
                    )
                )
                viewModel.postData(customer)
                viewModel.signupSuccess.observe(viewLifecycleOwner) {
                    if (it == true) {
                        Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_LONG).show()

                        findNavController().navigate(R.id.signInFragment)
                    } else Toast.makeText(requireContext(), "Try again", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        lineItem.add(Address(55555, "fffdsfsdf", "ddd", "firstName", "lastName", "55", true, "01026241542"))
        firstName = binding.etUsername.text.toString()
        userEmail = binding.etEmail.text.toString()
        userPassword = binding.etPassword.text.toString()
        userConfirmPassword = binding.etConfirmPassword.text.toString()
//        phone = binding.etPhone.text.toString()
        if (userEmail.isEmpty()) {
            binding.etEmail.requestFocus()
            binding.etEmail.error = "Required"
            return false
        }
        if (firstName.isEmpty()) {
            binding.etUsername.requestFocus()
            binding.etUsername.error = "Required"
            return false
        }

        if (userPassword.isEmpty()) {
            binding.etPassword.requestFocus()
            binding.etPassword.error = "Required"
            return false
        }
//        if (phone.isEmpty()) {
//            binding.etPhone.requestFocus()
//            binding.etPhone.error = "Required"
//            return false
//        }
        if (userConfirmPassword.isEmpty()) {
            binding.etConfirmPassword.requestFocus()
            binding.etConfirmPassword.error = "Required"
            return false
        }
        if (userPassword != userConfirmPassword) {
            binding.etConfirmPassword.requestFocus()
            binding.etConfirmPassword.error = "password doesn't match"
            return false
        }
        return true
    }

    private fun bindUi() {
        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }
        binding.ivClose.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}