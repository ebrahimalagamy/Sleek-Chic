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
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.viewmodels.CollectionProductsViewModel
import com.hema.e_commerce.ui.category.testmodels.SubCollections


class FragmentContainer : Fragment() {
    lateinit var binding: FragmentContainerBinding
    lateinit var adapter: ContainerAdapter
    private lateinit var viewModel: CollectionProductsViewModel
    lateinit var collectionName:String
    var fragMan: FragmentManager? = fragmentManager

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
        var collectionPosition: Int=
            when(collectionName){

                "Home Page"->  0
                "KID"-> 1
                "MEN"->2
                "SALE"-> 3
                "WOMEN"-> 4
                else -> 0
            }
        var subCollectionsListHome = arrayListOf<SubCollections>(

            //home 0
            SubCollections("T-SHIRTS", R.drawable.tshirthome),
            SubCollections("SHOES", R.drawable.homeshoes),
            SubCollections("ACCESSORIES", R.drawable.accessorieswoman)
        )


        initViews(collectionPosition)
        observe()

    }


    fun observe() {
        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer {
            var ProductList = it

            Log.i("ggggg", "onActivityCreated: " + ProductList.size)
            adapter = ContainerAdapter(fragMan, ProductList, requireContext())
            val layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recContainerItem.adapter = adapter
            binding.recContainerItem.layoutManager = layoutManager
        })
    }

    private fun initViews(position:Int) {
        viewModel.getProducts(position)
    }

}