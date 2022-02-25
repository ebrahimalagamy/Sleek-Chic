package com.hema.e_commerce.ui.category.categoryui.typelistofproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentTypeListProductBinding
import com.hema.e_commerce.ui.category.repository.Repository
import com.hema.e_commerce.ui.category.testmodels.TypeModelList


class TypeListProductsFragment : Fragment() {
    lateinit var binding: FragmentTypeListProductBinding
    lateinit var adapter: TypeListAdapter
    lateinit var arr: ArrayList<TypeModelList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_type_list_product, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arr = Repository().arrTypeList
        adapter = TypeListAdapter(arr, requireContext())
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recListProduct.adapter = adapter
        binding.recListProduct.layoutManager = layoutManager


    }
}