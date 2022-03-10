package com.hema.e_commerce.model.dataclass.customer

import com.google.gson.annotations.SerializedName


data class CustomersModel(
    @SerializedName( "customers")
    val customer: List<Customer?>,
)
