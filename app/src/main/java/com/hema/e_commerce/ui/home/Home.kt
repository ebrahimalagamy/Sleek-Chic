package com.hema.e_commerce.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
import com.hema.e_commerce.MainActivity
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.home.BrandAdapter
import com.hema.e_commerce.adapter.home.WishListNotificationAdapter
import com.hema.e_commerce.databinding.HomeFragmentBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.HomeViewModelFactory
import com.hema.e_commerce.model.viewmodels.HomeViewModel
import com.hema.e_commerce.ui.progresspar.ProgressBarSetting
import com.hema.e_commerce.util.Connectivity.Companion.isOnline
import com.hema.e_commerce.util.Constant
import com.hema.e_commerce.util.Constant.SIGN_IN
import com.hema.e_commerce.util.SharedPreferencesProvider


class Home : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var sharedPref: SharedPreferencesProvider

    //   private lateinit var productAdapter: ProductsAdapter
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        sharedPref = SharedPreferencesProvider(requireActivity())
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProgressBarSetting().setProgress(requireActivity())
        val repository = Repository(RoomData(requireContext()))
        val homeViewModelProviderFactory =
            HomeViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
        navController = Navigation.findNavController(requireView())

        imageSlider()
        initViews()
        observers()
        onClickSearch()
        iconBadges()


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //product and brand logic
    private fun observers() {
        observeBrand()
        // observeSaleProduct()
    }

    private fun observeBrand() {
        viewModel.brandsLiveData.observe(viewLifecycleOwner) {
            val brandList = it.smart_collections
            brandAdapter = BrandAdapter(arrayListOf())
            brandAdapter.updateBrand(brandList)
            binding.brandsRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            binding.brandsRecycler.layoutManager =
                GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false)


            binding.brandsRecycler.adapter = brandAdapter
        }
    }
    /*  fun observeSaleProduct() {
          viewModel.onSaleProducts .observe(viewLifecycleOwner, Observer {
              var productList=it.products
              productAdapter= ProductsAdapter(arrayListOf())
              productAdapter.updateproduct(productList)
              binding.bestSellingRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
              binding.bestSellingRecyclerView.adapter=productAdapter
          })
      }*/

    private fun initViews() {
        viewModel.getBrand()
        //   viewModel.getOnHomeProducts()
        //  viewModel.getOnSaleProducts()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
// image slide view
    private fun imageSlider() {
        //function for image slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.cubon1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubon3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubon4, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.nike1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubonkids, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.cubontshirt, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.shoes2, ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.imageSlider.setImageList(imageList)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
//search
    @RequiresApi(Build.VERSION_CODES.M)
    private fun onClickSearch() {
        binding.searchView.setOnSearchClickListener {
            binding.searchView.isIconified = true
            if (isOnline(requireContext())) {
                navController.navigate(
                    R.id.action_home_to_searchFragment
                )
            } else {

                Toast.makeText(requireContext(), R.string.check_internet, Toast.LENGTH_SHORT).show()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun iconBadges() {
        val wishListIcon = WishListNotificationAdapter(binding.favourite)
        if (sharedPref.isSignIn) {
            viewModel.getFavProducts(sharedPref.getUserInfo().customer?.customerId!!)
                .observe(viewLifecycleOwner, Observer {
                    wishListIcon.updateView(it.size)
                })
        } else {
            wishListIcon.hideNumber()
        }

        wishListIcon.Button.setOnClickListener {
            if (sharedPref.isSignIn) {
                findNavController().navigate(R.id.wishlist)
            } else {
                Snackbar.make(requireView(), R.string.sign_in_message, Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Sign In") {
                            navController.navigate(R.id.Settings)
                        }
                        show()
                    }
            }
        }
    }


}