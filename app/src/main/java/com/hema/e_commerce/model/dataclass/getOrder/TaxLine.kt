package com.hema.e_commerce.model.dataclass.getOrder

data class TaxLine(
    val price: String,
    val price_set: PriceSet,
    val rate: Double,
    val title: String
)