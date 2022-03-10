package com.hema.e_commerce.model.dataclass.customer

import com.google.gson.annotations.SerializedName

data class Addresses(

	@field:SerializedName("addresses")
	val addresses: List<Address?>? = null
)