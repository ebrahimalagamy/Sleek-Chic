package com.hema.e_commerce.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentOrderBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData

class OrderFragment:Fragment() {
    private lateinit var binding:FragmentOrderBinding
    private lateinit var viewModel: OrderFragmentViewModel
    private lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_order,container,false)
        val repository= Repository(RoomData(requireContext()))
        val orderViewModelProviderFactory = OrderFragmentViewModelFactory(requireActivity().application,repository)
        viewModel = ViewModelProvider(this,orderViewModelProviderFactory)[OrderFragmentViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        click()
    }

    fun click(){
        binding.tvCancelOrder.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_cancelledOrderFragment)
        }
    }
}