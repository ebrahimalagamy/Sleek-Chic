package com.hema.e_commerce.ui.settings.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentWishlistBinding


class Wishlist : Fragment() {
    private lateinit var binding: FragmentWishlistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false)
        return binding.root
    }

}