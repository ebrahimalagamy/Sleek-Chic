package com.hema.e_commerce.model.dataclass.getOrder

data class RefundLineItem(
    val id: Int,
    val line_item: LineItem,
    val line_item_id: Int,
    val location_id: Int,
    val quantity: Int,
    val restock_type: String,
    val subtotal: Double,
    val subtotal_set: SubtotalSet,
    val total_tax: Double,
    val total_tax_set: TotalTaxSet
)