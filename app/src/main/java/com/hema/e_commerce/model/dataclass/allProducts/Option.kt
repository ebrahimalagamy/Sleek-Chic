package com.hema.e_commerce.model.dataclass.allProducts

data class Option(
    val id: Double,
    val name: String,
    val position: Int,
    val product_id: Double,
    val values: List<String>
)