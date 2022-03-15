package com.hema.e_commerce.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.order.OrderAdapter
import com.hema.e_commerce.databinding.FragmentOrderBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.OrderFragmentViewModelFactory
import com.hema.e_commerce.model.viewmodels.OrderFragmentViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider

class OrderFragment:Fragment() {
    private lateinit var binding:FragmentOrderBinding
    private lateinit var viewModel: OrderFragmentViewModel
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var sharedPref: SharedPreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = SharedPreferencesProvider(requireActivity())
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_order,container,false)
        val repository= Repository(RoomData(requireContext()))
        val orderViewModelProviderFactory = OrderFragmentViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this,orderViewModelProviderFactory)[OrderFragmentViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        click()

        viewModel.getActiveStateOrder("ACTIVE",sharedPref.getUserInfo().customer?.customerId?:0).observe(viewLifecycleOwner, Observer {order->
            if (order.isEmpty()){
                binding.imageView6.visibility=View.VISIBLE
            }else{
                binding.imageView6.visibility=View.GONE

            }
            orderAdapter = OrderAdapter(order,viewModel,requireContext())
            setupRecyclerView()

        })



    }

    fun click(){
        binding.tvCancelOrder.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_cancelledOrderFragment)
        }
    }

    private fun setupRecyclerView(){
        binding.orderRecA.apply {
            adapter=orderAdapter
            layoutManager= LinearLayoutManager(context)

        }
    }

}