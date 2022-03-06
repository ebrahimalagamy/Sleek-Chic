package com.hema.e_commerce.model.dataclass.singleproduct

data class Option(
    val id: Long,
    val name: String,
    val position: Int,
    val product_id: Long,
    val values: List<String>
)