package com.hema.e_commerce.ui.settings.auth.login

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.hema.e_commerce.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var userEmail: String
    private lateinit var pass: String
    val viewModel by lazy {
        LoginViewModel.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUi()
        binding.btnSignIn.setOnClickListener {
            if (validteForm()) {
                Log.d("email", "" + userEmail)
                viewModel.getData(userEmail,pass)
                viewModel.loginSuccess.observe(viewLifecycleOwner) {
                    if (it!!) {
                        Toast.makeText(requireContext(), "Successfully Login", Toast.LENGTH_LONG)
                            .show()
                        findNavController().navigate(R.id.action_signInFragment_to_completeLoginFragment)
                    }
                }
            }
        }

//        binding.button2.setOnClickListener {
//            var name = binding.editTextTextPersonName.text.toString()
//            var id: Long? = viewModel.AuthRepo.sharedPref.getUserInfo().customer?.customerId
//            val address = AddressModel(
//                Address(
//                    address1 = name,
//                )
//            )
//            if (id != null) {
//                viewModel.address(id, address)
//            }
//            viewModel.address.observe(viewLifecycleOwner) {
//                if (it!!) {
//                    Toast.makeText(
//                        requireContext(),
//                        "updated",
//                        Toast.LENGTH_LONG
//                    ).show()
//                } else Toast.makeText(
//                    requireContext(),
//                    "update faild",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//        var body = viewModel.AuthRepo.sharedPref.getUserInfo().customer
//        Log.d("bogyjj", body.toString())

    }

    private fun validteForm(): Boolean {
        userEmail = binding.emailEditText.text.toString()
        pass =binding.passwordEditText.text.toString()

        if (userEmail.isEmpty()) {
            binding.emailEditText.requestFocus()
            binding.emailEditText.error = "Required"
            return false
        }
        if (pass.isEmpty()) {
            binding.passwordEditText.requestFocus()
            binding.passwordEditText.error = "Required"
            return false
        }

        return true

    }

    private fun bindUi() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.ivClose.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
}