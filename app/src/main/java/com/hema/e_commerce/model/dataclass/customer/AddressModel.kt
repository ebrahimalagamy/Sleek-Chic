package com.hema.e_commerce.model.dataclass.customer

import com.google.gson.annotations.SerializedName


data class AddressModel(
    @SerializedName( "address")
    val address: Address,
)
