package com.hema.e_commerce.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.models.SlideModel
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.HomeFragmentBinding


class Home : Fragment(R.layout.home_fragment) {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        //  (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageSlider()
//        binding.searchView.queryHint = "Custom Search Hint"

    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)

    }
*/
    private fun imageSlider() {
        //function for image slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.m))
        imageList.add(SlideModel(R.drawable.w1))
        imageList.add(SlideModel(R.drawable.m2))
        imageList.add(SlideModel(R.drawable.w2))
        imageList.add(SlideModel(R.drawable.m3))
        imageList.add(SlideModel(R.drawable.w3))
        binding.imageSlider.setImageList(imageList)
    }
}