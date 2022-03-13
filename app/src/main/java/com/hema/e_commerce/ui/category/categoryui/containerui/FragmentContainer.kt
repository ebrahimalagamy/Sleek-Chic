package com.hema.e_commerce.ui.category.categoryui.containerui

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
import androidx.recyclerview.widget.GridLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.catagory.ContainerAdapter
import com.hema.e_commerce.databinding.FragmentContainerBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.SubCollectionViewModelFactory
import com.hema.e_commerce.model.viewmodels.SubCollectionViewModel
import com.hema.e_commerce.util.Constant.HOME_PAGE_ID
import com.hema.e_commerce.util.Constant.KIDS_ID
import com.hema.e_commerce.util.Constant.MEN_ID
import com.hema.e_commerce.util.Constant.SALE_ID
import com.hema.e_commerce.util.Constant.WOMAN_ID


class FragmentContainer : Fragment() {
    lateinit var binding: FragmentContainerBinding
    lateinit var adapter: ContainerAdapter
    private lateinit var viewModel: SubCollectionViewModel
    lateinit var collectionName: String
    var fragMan: FragmentManager? = fragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_container, container, false)
        val repository = Repository(RoomData(requireContext()))
        val subCollectionViewModelProviderFactory =
            SubCollectionViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(
            this,
            subCollectionViewModelProviderFactory
        )[SubCollectionViewModel::class.java]


        var bundle: Bundle? = arguments
        if (bundle != null) {
            if (bundle.getString("title") != null) {
                bundle.getString("title")
                collectionName = bundle.getString("title").toString()
            }

        }


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.subcollectionName.text = collectionName
        var collectionPosition: Int =
            when (collectionName) {

                getString(R.string.home_page) -> 0
                getString(R.string.kid) -> 1
                getString(R.string.men) -> 2
                getString(R.string.sale) -> 3
                getString(R.string.woman) -> 4
                else -> 0
            }

        initViews(collectionPosition)
        observe()

    }


    fun observe() {
        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            var ProductList = it

            Log.i("ggggg", "onActivityCreated: " + ProductList.size)
            var collectionId: Long =
                when (collectionName) {

                    getString(R.string.home_page) -> HOME_PAGE_ID
                    getString(R.string.kid) -> KIDS_ID
                    getString(R.string.men) -> MEN_ID
                    getString(R.string.sale) -> SALE_ID
                    getString(R.string.woman) -> WOMAN_ID
                    else -> HOME_PAGE_ID
                }
            adapter = ContainerAdapter(collectionId, fragMan, ProductList, requireContext())
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recContainerItem.adapter = adapter
            binding.recContainerItem.layoutManager = layoutManager
        })
    }

    private fun initViews(position: Int) {
        viewModel.getProducts(position)
    }

}