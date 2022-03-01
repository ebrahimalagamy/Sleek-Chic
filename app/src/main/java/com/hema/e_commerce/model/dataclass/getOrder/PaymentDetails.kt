package com.hema.e_commerce.model.dataclass.getOrder

data class PaymentDetails(
    val avs_result_code: Any,
    val credit_card_bin: Any,
    val credit_card_company: String,
    val credit_card_number: String,
    val cvv_result_code: Any
)