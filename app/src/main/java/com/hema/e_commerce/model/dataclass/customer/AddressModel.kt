package com.hema.e_commerce.model.dataclass.customer

import com.google.gson.annotations.SerializedName
import com.hema.e_commerce.ui.settings.address.Address


data class AddressModel(
    @SerializedName( "address")
    val address: com.hema.e_commerce.model.dataclass.customer.Address,
)
