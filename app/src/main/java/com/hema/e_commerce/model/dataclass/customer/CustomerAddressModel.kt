package com.stash.shopeklobek.model.entities

import com.google.gson.annotations.SerializedName
import com.hema.e_commerce.model.dataclass.customer.CustomerAddress

data class CustomerAddressModel(
    @SerializedName( "customer_address")
    val customerAddress: CustomerAddress?,
)
