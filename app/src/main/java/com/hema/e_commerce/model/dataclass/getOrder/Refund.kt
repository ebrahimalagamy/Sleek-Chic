package com.hema.e_commerce.model.dataclass.getOrder

data class Refund(
    val admin_graphql_api_id: String,
    val created_at: String,
    val id: Int,
    val note: String,
    val order_adjustments: List<Any>,
    val order_id: Int,
    val processed_at: String,
    val refund_line_items: List<RefundLineItem>,
    val restock: Boolean,
    val transactions: List<Transaction>,
    val user_id: Int
)