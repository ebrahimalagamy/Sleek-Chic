package com.hema.e_commerce.model.dataclass.allProducts

data class Image(
    val admin_graphql_api_id: String,
    val alt: Any,
    val created_at: String,
    val height: Int,
    val id: Double,
    val position: Int,
    val product_id: Double,
    val src: String,
    val updated_at: String,
    val variant_ids: List<Any>,
    val width: Int
)