package com.hema.e_commerce.model.dataclass.customer

import com.google.gson.annotations.SerializedName

data class Address(

	@field:SerializedName("zip")
	val zip: String? = null,

	@field:SerializedName("country")
	val country: Any? = null,

	@field:SerializedName("address2")
	val address2: Any? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("address1")
	val address1: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("province_code")
	val provinceCode: Any? = null,

	@field:SerializedName("country_code")
	val countryCode: Any? = null,

	@field:SerializedName("default")
	val jsonMemberDefault: Boolean? = null,

	@field:SerializedName("province")
	val province: Any? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("country_name")
	val countryName: Any? = null,

	@field:SerializedName("company")
	val company: Any? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("customer_id")
	val customerId: Long? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null
)