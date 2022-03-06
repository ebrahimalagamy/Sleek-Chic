package com.hema.e_commerce.model.dataclass.customer

import com.google.gson.annotations.SerializedName


data class CustomerLoginModel(
    @SerializedName( "customers")
    val customer: List<Customer?>,

    @SerializedName( "error")
    val error: Error? = null,
)
