package com.hema.e_commerce.ui.category.singleproduct

data class Option(
    val id: Long,
    val name: String,
    val position: Int,
    val product_id: Long,
    val values: List<String>
)