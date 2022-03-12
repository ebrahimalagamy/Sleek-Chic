package com.hema.e_commerce.ui.settings.checkout

import android.app.Activity
import android.content.Intent
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
import com.hema.e_commerce.util.Constant.CLIENT_ID
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.random.Random

class Checkout : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private val args: CheckoutArgs by navArgs()
    private lateinit var viewModel: CheckoutViewModel
    private lateinit var config: PayPalConfiguration

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
        bindUI()
        bindPaypal()

    }

    private fun bindPaypal() {
        config = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(CLIENT_ID)
        var i = Intent(requireActivity(), PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        requireActivity().startService(i)
    }

    override fun onDestroy() {
        requireActivity().stopService(Intent(requireActivity(), PayPalService::class.java))
        super.onDestroy()
    }

    private fun bindArgs() {
        val totalPrice = args.totalPrice
        binding.tvTotalPrice.text = totalPrice
        binding.tvSubtotal.text = totalPrice
    }

    private fun bindUI() {
        val orderId = Random.nextLong(1000000, 10000000)
        val customerName = binding.tvCustomerName.text.toString()
        val customerAddress = binding.tvCustomerAddress.text.toString()
        val customerPhone = binding.tvCustomerPhone.text.toString()
        var paymentMethod: String? = null
        val totalPrice = binding.tvTotalPrice.text.toString()

        binding.btnOrder.setOnClickListener {
            val rbSelectedId = binding.rgGroup.checkedRadioButtonId
            val btn = requireView().findViewById<RadioButton>(rbSelectedId)
            if (btn != null) {
                paymentMethod = btn.text.toString()
            }

            when (paymentMethod) {
                getString(R.string.pay_with_cash) -> {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle(getString(R.string.currency))
                        .setMessage(getString(R.string.do_you_want_confirm_your_order))
                        .setPositiveButton(getString(R.string.ok)) { _, _ ->
                            Toast.makeText(requireActivity(), getString(R.string.confirmed), Toast.LENGTH_SHORT)
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
                        .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                            Toast.makeText(requireActivity(), getString(R.string.order_oanceled), Toast.LENGTH_SHORT)
                                .show()
                        }.show()


                }
                getString(R.string.pay_with_paypal) -> {
                    val totalPaypal = totalPrice.toDouble() / 15.7
                    var payment = PayPalPayment(
                        BigDecimal.valueOf(totalPaypal),
                        "USD",
                        "Checkout",
                        PayPalPayment.PAYMENT_INTENT_SALE
                    )
                    val intent = Intent(requireActivity(), PaymentActivity::class.java)
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
                    requireActivity().startActivityForResult(intent, 123)
                }
                else -> {
                    Toast.makeText(requireActivity(), getString(R.string.select_way_to_pay), Toast.LENGTH_LONG).show()
                }

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(requireActivity(), getString(R.string.operation_done), Toast.LENGTH_SHORT).show()
            }
        }
    }


}