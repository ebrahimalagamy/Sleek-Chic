package com.hema.e_commerce.ui.settings.auth.password

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.SigninPasswordFragmentBinding

class SignInPasswordFragment : Fragment() {

    private lateinit var binding: SigninPasswordFragmentBinding
    private lateinit var userPass: String

    private val viewModel by lazy {
        SignInPasswordViewModel.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.signin_password_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            userPass = binding.edtpassword.text.toString()
            Log.d("hemaaaaa", "" + viewModel.authenticationRepo.sharedPref.getSettings().customer!!.lastName)

            if (viewModel.authenticationRepo.sharedPref.getSettings().customer!!.lastName.equals(userPass)
            ) {
                Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_LONG).show()
            }
        }
       viewModel.authenticationRepo.sharedPref.getSettings()
    }
}