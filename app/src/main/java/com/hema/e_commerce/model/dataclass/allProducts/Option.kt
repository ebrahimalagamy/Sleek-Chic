package com.hema.e_commerce.model.dataclass.allProducts

data class Option(
    val id: Long,
    val name: String,
    val position: Int,
    val product_id: Long,
    var values: List<String>
)