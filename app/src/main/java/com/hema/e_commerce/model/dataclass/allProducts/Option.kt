package com.hema.e_commerce.model.dataclass.allProducts

data class Option(
    val id: Int,
    val name: String,
    val position: Int,
    val product_id: Int,
    val values: List<String>
)