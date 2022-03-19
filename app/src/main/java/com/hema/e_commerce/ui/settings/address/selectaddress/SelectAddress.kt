package com.hema.e_commerce.ui.settings.address.selectaddress

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hema.e_commerce.R
import com.hema.e_commerce.adapter.address.AddressAdapter
import com.hema.e_commerce.adapter.address.RecyclerOnItemTouchLestiner
import com.hema.e_commerce.adapter.address.SelectedAdapter
import com.hema.e_commerce.databinding.FragmentSelectAddressBinding
import com.hema.e_commerce.util.SharedPreferencesProvider

class SelectAddress : Fragment() {

    private lateinit var binding: FragmentSelectAddressBinding
    private lateinit var sharedPref: SharedPreferencesProvider
    private val viewModel by lazy {
        SelectAddressesViewModel.create(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_address, container, false)
        binding.vm = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SelectedAdapter(viewModel.list.addresses!!, view.context)
        sharedPref = SharedPreferencesProvider(requireActivity())
        binding.recycler.layoutManager = LinearLayoutManager(view.context)

        binding.recycler.addOnItemTouchListener(
            RecyclerOnItemTouchLestiner(context, binding.recycler, object :
                RecyclerOnItemTouchLestiner.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    sharedPref.saveAddress(viewModel.list.addresses!![position]!!)
                    findNavController().popBackStack()
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    // do whatever
                }
            })
        )
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
//        removeItemRecycler(binding.recycler, adapter)
        bindUi()

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
                val id = adapter.removeItem(position)
                viewModel.delete(id)
                adapter.notifyDataSetChanged()
                Toast.makeText(requireActivity(), "Location deleted", Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv)
    }


    private fun bindUi() {
//        binding.btnAddAddress.setOnClickListener {
//            findNavController().navigate(R.id.action_address_to_addAddressFragment)
//        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

}