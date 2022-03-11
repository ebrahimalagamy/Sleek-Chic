package com.hema.e_commerce.ui.settings.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hema.e_commerce.R
import com.hema.e_commerce.databinding.FragmentCheckoutBinding
import com.hema.e_commerce.model.repository.Repository
import com.hema.e_commerce.model.room.RoomData
import com.hema.e_commerce.model.room.orderroom.OrderData
import com.hema.e_commerce.model.viewmodels.CheckoutViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class Checkout : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private val args: CheckoutArgs by navArgs()
    private lateinit var viewModel: CheckoutViewModel

//  private lateinit var config: Paypal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)
        val repository = Repository(RoomData(requireContext()))
        val checkoutViewModelProviderFactory =
            CheckoutViewModelFactory(requireActivity().application, repository)
        viewModel =
            ViewModelProvider(this, checkoutViewModelProviderFactory)[CheckoutViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindArgs()
        GlobalScope.launch(Dispatchers.IO) {
            bindUI()
        }

    }

    private fun bindArgs() {
        val totalPrice = args.totalPrice
        binding.tvTotalPrice.text = totalPrice
        binding.tvSubtotal.text = totalPrice
    }

    private suspend fun bindUI() {
        val orderId = Random.nextLong(1000000, 10000000)
        val customerName = binding.tvCustomerName.text.toString()
        val customerAddress = binding.tvCustomerAddress.text.toString()
        val customerPhone = binding.tvCustomerPhone.text.toString()
        var paymentMethod: String? = null
        val totalPrice = binding.tvTotalPrice.text.toString()

        binding.btnOrder.setOnClickListener {
            val rbSelectedId = binding.rgGroup.checkedRadioButtonId
            val btn = requireView().findViewById<RadioButton>(rbSelectedId)
            paymentMethod = btn.text.toString()

            when (paymentMethod) {
                "Pay With Cash" -> {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("Currency")
                        .setMessage("Do you Want Confirm Your Order")
                        .setPositiveButton("Ok") { _, _ ->
                            Toast.makeText(requireActivity(), "Confirmed", Toast.LENGTH_SHORT)
                                .show()
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.addOrder(
                                    OrderData(
                                        orderId,
                                        totalPrice,
                                        customerName,
                                        customerAddress,
                                        customerPhone,
                                        paymentMethod!!,
                                        "ACTIVE"
                                    )
                                )

                            }
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            Toast.makeText(requireActivity(), "Order Canceled", Toast.LENGTH_SHORT)
                                .show()
                        }.show()


                }
                "Pay With Paypal" -> {
                    Toast.makeText(requireActivity(), "Not Active Else", Toast.LENGTH_LONG).show()
                }
                "Pay With Card" -> {
                    Toast.makeText(requireActivity(), "Not Active Else", Toast.LENGTH_LONG).show()
                }

            }

        }

    }


}