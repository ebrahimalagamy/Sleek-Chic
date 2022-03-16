package com.hema.e_commerce.model.dataclass.currancy

data class CurrancyResponse(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val motd: Motd,
    val query: Query,
    val result: Double,
    val success: Boolean
)