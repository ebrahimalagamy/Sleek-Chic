package com.hema.e_commerce.ui.wishlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.wishList.WishListAdapter
import com.hema.e_commerce.databinding.FragmentWishlistBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.WishListViewModelFactory
import com.hema.e_commerce.model.viewmodels.WishListViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider


class Wishlist : Fragment() {
    private lateinit var binding: FragmentWishlistBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var favAdapter: WishListAdapter
    private lateinit var sharedPref: SharedPreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = SharedPreferencesProvider(requireActivity())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false)
        val repository= Repository(RoomData(requireContext()))
        val wishListViewModelProviderFactory = WishListViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this,wishListViewModelProviderFactory)[WishListViewModel::class.java]
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.getFavProducts(sharedPref.getUserInfo().customer?.customerId?:0).observe(viewLifecycleOwner, Observer {product->
            if (product.isEmpty()){
                binding.imageView4.visibility=View.VISIBLE
            }else{
                binding.imageView4.visibility=View.GONE

            }
            favAdapter.differ.submitList(product)
            Log.d("product",product.size.toString())
        })

        //swap to delete
        val itemTouchCallBack=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val favProduct=favAdapter.differ.currentList[position]
                viewModel.delete(favProduct)
                Snackbar.make(view,"Successfully Deleted Product", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.insertFav(favProduct)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchCallBack).apply {
            attachToRecyclerView(binding.favRec)
        }
    }


    private fun setupRecyclerView(){
        favAdapter = WishListAdapter(requireContext(),viewModel)
        binding.favRec.apply {
            adapter=favAdapter
            layoutManager= LinearLayoutManager(context)

        }
    }


}