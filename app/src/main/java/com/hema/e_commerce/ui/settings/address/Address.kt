package com.hema.e_commerce.ui.settings.address

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.address.AddressAdapter
import com.hema.e_commerce.databinding.FragmentAddressBinding
import com.hema.e_commerce.model.room.orderroom.OrderData
import com.hema.e_commerce.util.SharedPreferencesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Address : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private lateinit var sharedPref: SharedPreferencesProvider
     lateinit var adapter: AddressAdapter
    private val viewModel by lazy {
        AddressesViewModel.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Address","onCreateView")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            viewModel.getAdressesList()
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        binding.vm = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Address","onViewCreated")

        viewModel.list.observe(requireActivity(), Observer {
            adapter = AddressAdapter(it.addresses!!, view.context)
            //sharedPref = SharedPreferencesProvider(requireActivity())
            binding.recycler.layoutManager = LinearLayoutManager(view.context)
            binding.recycler.setHasFixedSize(true)
            binding.recycler.adapter = adapter
            removeItemRecycler(binding.recycler, adapter)
            bindUi()
        })


    }

    fun removeItemRecycler(rv: RecyclerView, adapter: AddressAdapter) {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            @SuppressLint("NotifyDataSetChanged")
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition

                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("Address")
                    .setMessage("Do you want delete this address")
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        val id = adapter.removeItem(position)
                        viewModel.delete(id)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(requireActivity(), "Location deleted", Toast.LENGTH_SHORT).show()                    }
                    .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        adapter.notifyDataSetChanged()
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.order_oanceled),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }.show()

            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv)
    }


    private fun bindUi() {
        binding.btnAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_address_to_addAddressFragment)
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()

        }


    }

    override fun onPause() {
        super.onPause()

        Log.d("Address","onPause")

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        Log.d("Address","resume")
    }
}

