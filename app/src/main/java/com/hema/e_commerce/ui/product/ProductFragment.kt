package com.hema.e_commerce.ui.product

import android.content.SharedPreferences
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.home.WishListNotificationAdapter
import com.hema.e_commerce.adapter.singleProduct.ProductAdapter
import com.hema.e_commerce.databinding.FragmentProductBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.room.cartroom.CartProductData
import com.hema.e_commerce.model.room.favoriteRoom.FavoriteProduct
import com.hema.e_commerce.model.viewModelFactory.SingleProductViewModelFactory
import com.hema.e_commerce.model.viewmodels.SingleProductViewModel
import com.hema.e_commerce.ui.progresspar.ProgressBarSetting
import com.hema.e_commerce.util.Constant.FAVORITE
import com.hema.e_commerce.util.Constant.PRODUCT
import com.hema.e_commerce.util.SharedPreferencesProvider


class ProductFragment : Fragment() {
    lateinit var navController: NavController
    private lateinit var viewModel: SingleProductViewModel
    lateinit var binding: FragmentProductBinding
    private lateinit var sharedPref: SharedPreferencesProvider

    private var productId: Long? = null
    private lateinit var favProduct: FavoriteProduct
    private var isFavBtnClicked: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        val repository = Repository(RoomData(requireContext()))
        val singleProductsViewModelProviderFactory =
            SingleProductViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(
            this,
            singleProductsViewModelProviderFactory
        )[SingleProductViewModel::class.java]
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProgressBarSetting().setProgress(requireActivity())
        sharedPref = SharedPreferencesProvider(requireActivity())
        productId = arguments?.getLong(PRODUCT)
        initViews(productId!!)
        observe(savedInstanceState)
        reviewAction()
        iconBadges()
        back()


    }

    fun observe(savedInstanceState: Bundle?) {
        viewModel.singleProductLiveData.observe(viewLifecycleOwner) {

            val adapter: PagerAdapter = ProductAdapter(requireContext(), it.product.images)
            val product = it.product
            binding.viewPager.adapter = adapter
            it.product.handle
            Log.i("n", "observe: " + it.product.handle)
            binding.tvDescProduct.text = it.product.body_html

            val sharedPreferences: SharedPreferences =
                requireContext().getSharedPreferences("currency", 0)
            when (sharedPreferences.getString("currency", "EGP")) {
                "EGP" -> binding.tvPrice.text =
                    it.product.variants[0].price + " " + getString(R.string.eg)
                "USA" -> {
                    val usCurrancy = ((it.product.variants[0].price).toDouble() / (15.71))
                    val number: Double = String.format("%.2f", usCurrancy).toDouble()
                    binding.tvPrice.text = "$number $"

                }
                "EUR" -> {
                    val ureCurrancy = ((it.product.variants[0].price).toDouble() / (17.10))
                    val number: Double = String.format("%.2f", ureCurrancy).toDouble()
                    binding.tvPrice.text = number.toString() + " " + getString(R.string.eur)
                }
                else -> binding.tvPrice.text =
                    it.product.variants[0].price + " " + getString(R.string.eg)

            }
            binding.tvTitle.text = it.product.title

            binding.addToCart.setOnClickListener {
                //befor that we will check if user login or not
                if (sharedPref.isSignIn) {
                    val cartitem = CartProductData(
                        product.id,
                        sharedPref.getUserInfo().customer?.customerId,
                        product.image.src,
                        product.title,
                        product.variants[0].price,
                        product.variants[0].inventory_quantity,
                        1
                    )
                    if (product.variants[0].inventory_quantity > 0) {
                        viewModel.saveCartList(cartitem)
                        Toast.makeText(context, R.string.add_to_cart, Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "this product not available in  stor now",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

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

            //favorite function
            favProduct = FavoriteProduct(
                product.id,
                sharedPref.getUserInfo().customer?.customerId,
                product.image.src,
                product.title,
                product.variants[0].price,
                product.variants[0].inventory_quantity,
                1
            )
            setFav(savedInstanceState)

        }

    }

    private fun reviewAction() {
        navController = Navigation.findNavController(binding.root)
        binding.tvReviews.setOnClickListener {
            navController.navigate(R.id.action_productFragment_to_reviewFragment)
        }
    }

    private fun initViews(productId: Long) {
        viewModel.getSingleProduct(productId)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//favorite Logic Mohamed
    private fun setFav(savedInstanceState: Bundle?) {
        val activity = activity
        if (activity != null && isAdded) {
            if (savedInstanceState != null) {
                isFavBtnClicked = savedInstanceState.getBoolean(FAVORITE)
                setStoredButton(isFavBtnClicked)
            } else {
                checkWishListStored(productId ?: 0)
            }

            binding.favButton.setOnClickListener {
                if (sharedPref.isSignIn) {

                    isFavBtnClicked = if (isFavBtnClicked) {
                        viewModel.deleteByID(productId ?: 0)
                        false
                    } else {
                        viewModel.insertFav(favProduct)
                        true
                    }
                    setStoredButton(isFavBtnClicked)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(FAVORITE, isFavBtnClicked)
    }

    private fun checkWishListStored(id: Long) {
        viewModel.getOneItemFromRoom(id, sharedPref.getUserInfo().customer?.customerId!!)
            .observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    isFavBtnClicked = true
                    setStoredButton(isFavBtnClicked)
                }
            })
    }

    private fun setStoredButton(isFavBtnClicked: Boolean) {
        if (isFavBtnClicked) {
            binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.favButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    fun setRating() {
        binding.ratingBar.setOnClickListener {

        }

    }

    fun back(){
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun iconBadges() {
        val wishListIcon = WishListNotificationAdapter(binding.favourite)
        if (sharedPref.isSignIn) {
            viewModel.getFavProducts(sharedPref.getUserInfo().customer?.customerId!!)
                .observe(viewLifecycleOwner) {
                    wishListIcon.updateView(it.size)
                }
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