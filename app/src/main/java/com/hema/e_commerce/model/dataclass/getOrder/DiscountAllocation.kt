package com.hema.e_commerce.model.dataclass.getOrder

data class DiscountAllocation(
    val amount: String,
    val amount_set: AmountSet,
    val discount_application_index: Int
)