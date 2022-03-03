package com.hema.e_commerce.model.dataclass.collects

data class Collect(
    val collection_id: Long,
    val created_at: String,
    val id: Long,
    val position: Int,
    val product_id: Long,
    val sort_value: String,
    val updated_at: String
)