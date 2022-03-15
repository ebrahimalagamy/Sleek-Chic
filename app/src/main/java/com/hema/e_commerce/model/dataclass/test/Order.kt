package com.hema.e_commerce.model.dataclass.test

data class Order(
//    val billing_address: BillingAddress,
//    val discount_codes: List<DiscountCode>,
    val email: String,
    val financial_status: String,
    val line_items: List<LineItem>,
    val phone: String,
//    val shipping_address: ShippingAddress,
//    val transactions: List<Transaction>
)