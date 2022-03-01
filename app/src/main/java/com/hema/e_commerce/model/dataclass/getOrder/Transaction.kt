package com.hema.e_commerce.model.dataclass.getOrder

data class Transaction(
    val admin_graphql_api_id: String,
    val amount: String,
    val authorization: String,
    val created_at: String,
    val currency: String,
    val device_id: Any,
    val error_code: Any,
    val gateway: String,
    val id: Int,
    val kind: String,
    val location_id: Any,
    val message: Any,
    val order_id: Int,
    val parent_id: Int,
    val processed_at: String,
    val receipt: Receipt,
    val source_name: String,
    val status: String,
    val test: Boolean,
    val user_id: Any
)