package com.hema.e_commerce.ui.settings.address

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hema.e_commerce.databinding.FragmentAddAddressBinding
import com.hema.e_commerce.util.Either
import com.hema.e_commerce.util.RepoErrors

import kotlinx.coroutines.launch

class AddAddressFragment : Fragment() {

    val binding by lazy {
        FragmentAddAddressBinding.inflate(layoutInflater)
    }

    val viewModel by lazy {
        AddAddressViewModel.create(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}