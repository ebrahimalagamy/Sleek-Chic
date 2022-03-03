package com.hema.e_commerce.ui.category.categoryui.maincategoryui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.CategoryFragmentBinding
import com.hema.e_commerce.model.viewmodels.CategoryViewModel

class CategoryFragment : Fragment() {
    lateinit var productAdapter: CategoriesProductAdapter
    lateinit var binding: CategoryFragmentBinding
    val TAG = "Category"


    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.category_fragment, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        initViews()
        observe()

    }

    fun observe() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer {
            var fragMan: FragmentManager? = fragmentManager
            var categoriesList = it.custom_collections

            Log.i(TAG, "onActivityCreated: " + categoriesList.size)
            productAdapter = CategoriesProductAdapter(fragMan, categoriesList, requireContext())
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.recProduct.adapter = productAdapter
            binding.recProduct.layoutManager = layoutManager
        })
    }

    private fun initViews() {
        viewModel.getCollections()
    }


}