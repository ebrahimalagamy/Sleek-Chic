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
import com.hema.e_commerce.databinding.FragmentContainerBinding
import com.hema.e_commerce.ui.category.viewmodels.CollectionProductsViewModel
import com.hema.e_commerce.util.Constant.HOME_PAGE_ID
import com.hema.e_commerce.util.Constant.KIDS_ID
import com.hema.e_commerce.util.Constant.MEN_ID
import com.hema.e_commerce.util.Constant.SALE_ID
import com.hema.e_commerce.util.Constant.WOMAN_ID


class FragmentContainer : Fragment() {
    lateinit var binding: FragmentContainerBinding
    lateinit var adapter: ContainerAdapter
    private lateinit var viewModel: CollectionProductsViewModel
    lateinit var collectionName:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_container, container, false)

        viewModel = ViewModelProvider(this).get(CollectionProductsViewModel::class.java)



        var bundle:Bundle?= arguments
       if(bundle!=null){
            Log.i("ssssssssssssssssss", "onCreateView: bundel ! null")

            if(bundle.getString("title")!=null) {
                bundle.getString("title")
                Log.i("ssssssssssssssssss", "onCreateView: "+bundle.getString("title"))
                collectionName=bundle.getString("title").toString()
            }
            else{
                Log.i("ssssssssssssssssss", "onCreateView: empty string ")
            }
        }

        else{

            Log.i("ssssssssssssssssss", "onCreateView:bundel = null ")


        }

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var collectionId: Long=
        when(collectionName){

            "Home Page"->  HOME_PAGE_ID
            "KID"-> KIDS_ID
            "MEN"->MEN_ID
            "SALE"-> SALE_ID
            "WOMEN"-> WOMAN_ID
            else -> 0
        }
        initViews(collectionId)
        observe()

    }


    fun observe() {
        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            var fragMan: FragmentManager? = fragmentManager
            var ProductList = it.products

            Log.i("ggggg", "onActivityCreated: " + ProductList.size)
            adapter = ContainerAdapter(fragMan, ProductList, requireContext())
            val layoutManager = GridLayoutManager(requireContext(), 3)
            binding.recContainerItem.adapter = adapter
            binding.recContainerItem.layoutManager = layoutManager
        })
    }

    private fun initViews(categoryId:Long) {
        viewModel.getProducts(categoryId)
    }

}