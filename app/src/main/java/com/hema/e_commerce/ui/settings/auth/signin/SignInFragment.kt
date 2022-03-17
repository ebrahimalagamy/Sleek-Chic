package com.hema.e_commerce.ui.settings.auth.signin

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
import com.hema.e_commerce.util.Constant
import com.hema.e_commerce.util.Constant.SIGN_IN


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var userEmail: String
    private lateinit var pass: String
    val viewModel by lazy {
        SignInViewModel.create(this)
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
                Log.d("email", "" + userEmail+"pass"+pass)
                viewModel.getData(userEmail,pass)
                viewModel.mldSignIn.observe(viewLifecycleOwner) {
                    if (it!!) {
                        Toast.makeText(requireContext(), "Successfully Login", Toast.LENGTH_LONG).show()

                        viewModel.AuthRepo.sharedPref.checkSignIn(true)
                        val brandBundle=Bundle().apply {
                            putBoolean(SIGN_IN,true)
                        }

                        findNavController().navigate(R.id.Settings,brandBundle)
                    }
                }
            }
        }

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