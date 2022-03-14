package com.hema.e_commerce.ui.category.categoryui.maincategoryui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.catagory.CategoriesProductAdapter
import com.hema.e_commerce.databinding.CategoryFragmentBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.CatagoryViewModelFactory
import com.hema.e_commerce.model.viewmodels.CategoryViewModel
import com.hema.e_commerce.ui.category.categoryui.containerui.FragmentContainer
import com.hema.e_commerce.ui.progresspar.ProgressBarSetting
import com.hema.e_commerce.util.Connectivity

class CategoryFragment : Fragment() {
    lateinit var productAdapter: CategoriesProductAdapter
    lateinit var binding: CategoryFragmentBinding
    val TAG = "Category"
    private lateinit var navController: NavController


    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.category_fragment, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ProgressBarSetting().setProgress(requireActivity())
        navController= Navigation.findNavController(requireView())
        val repository= Repository(RoomData(requireContext()))
        val catagoryViewModelProviderFactory =CatagoryViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this,catagoryViewModelProviderFactory)[CategoryViewModel::class.java]
        initViews()
        observe()


        var fragContainer: FragmentContainer = FragmentContainer()
        var  bundle: Bundle = Bundle()
        bundle.putString("title","Home Page")
        var fragMan: FragmentManager? = fragmentManager

        fragContainer.arguments=bundle
        fragMan!!.beginTransaction().replace(R.id.fram_cont, fragContainer).commit()

        onClickSearch()

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

    //search
    @RequiresApi(Build.VERSION_CODES.M)
    private fun onClickSearch(){
        binding.searchView.setOnSearchClickListener {
            if(Connectivity.isOnline(requireContext())){
                binding.searchView.isIconified = true
                navController.navigate(
                    R.id.action_category_to_searchFragment
                )
            }else{
                Toast.makeText(requireContext(), R.string.check_internet, Toast.LENGTH_SHORT).show()
            }
        }
    }

}