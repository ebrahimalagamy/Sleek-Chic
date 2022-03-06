package com.hema.e_commerce.model.dataclass.customer

import com.google.gson.annotations.SerializedName

data class CustomerModel (
    @SerializedName( "customer")
    val customer: Customer?,

    @SerializedName( "error")
    val error: Error? = null,
)

