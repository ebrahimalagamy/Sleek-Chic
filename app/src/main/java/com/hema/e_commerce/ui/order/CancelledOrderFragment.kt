package com.hema.e_commerce.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.order.CancelledOrderAdapter
import com.hema.e_commerce.databinding.FragmentOrderCancelledBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.viewModelFactory.OrderFragmentViewModelFactory
import com.hema.e_commerce.model.viewmodels.OrderFragmentViewModel
import com.hema.e_commerce.util.SharedPreferencesProvider

class CancelledOrderFragment:Fragment() {
    private lateinit var binding:FragmentOrderCancelledBinding
    private lateinit var viewModel: OrderFragmentViewModel
    private lateinit var cancelAdapter: CancelledOrderAdapter
    private lateinit var sharedPref: SharedPreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = SharedPreferencesProvider(requireActivity())
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_order_cancelled,container,false)
        val repository= Repository(RoomData(requireContext()))
        val orderViewModelProviderFactory = OrderFragmentViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this,orderViewModelProviderFactory)[OrderFragmentViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getActiveStateOrder("CANCELLED",sharedPref.getUserInfo().customer?.customerId?:0).observe(viewLifecycleOwner, Observer {order->
            cancelAdapter = CancelledOrderAdapter(order,viewModel,requireContext())
            setupRecyclerView()

        })
    }

    private fun setupRecyclerView(){
        binding.orderRec.apply {
            adapter=cancelAdapter
            layoutManager= LinearLayoutManager(context)

        }
    }
}
