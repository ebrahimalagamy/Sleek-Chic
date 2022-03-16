package com.hema.e_commerce.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            if (onBoardFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_home)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }

        }, 2000)
    }

    private fun onBoardFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoard", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }


}