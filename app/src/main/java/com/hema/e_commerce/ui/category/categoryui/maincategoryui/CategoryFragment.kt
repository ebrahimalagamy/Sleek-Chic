package com.hema.e_commerce.ui.category.categoryui.maincategoryui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.CategoryFragmentBinding
import com.hema.e_commerce.ui.category.repository.Repository
import com.hema.e_commerce.ui.category.testmodels.model
import com.hema.e_commerce.ui.category.viewmodel.CategoryViewModel

class CategoryFragment : Fragment() {
lateinit var productAdapter: CategoriesProductAdapter
lateinit var binding: CategoryFragmentBinding
lateinit var mo: model
lateinit var arr:ArrayList<model>
val TAG="Category"
    companion object {
        fun newInstance() = CategoryFragment()
    }

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
        var fragMan: FragmentManager? =fragmentManager

        Log.i(TAG, "onActivityCreated:XXXXXXXXXXXXXXXXXXXXXXXXXx "+ Repository().arr.size)
        arr= Repository().arr
        productAdapter = CategoriesProductAdapter(fragMan,arr, requireContext())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recProduct.adapter=productAdapter
        binding.recProduct.layoutManager=layoutManager


    }

}