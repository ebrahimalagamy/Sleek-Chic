package com.hema.e_commerce.model.dataclass.customer

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AddressArray(

	@field:SerializedName("addresses")
	val addresses: List<AddressesItem?>? = null
)

data class AddressesItem(

	@field:SerializedName("zip")
	val zip: String? = null,

	@field:SerializedName("country")
	val country: Any? = null,

	@field:SerializedName("address2")
	val address2: Any? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("address1")
	var address1: String? = null,

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
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		TODO("country"),
		TODO("address2"),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		TODO("provinceCode"),
		TODO("countryCode"),
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		TODO("province"),
		parcel.readString(),
		parcel.readString(),
		TODO("countryName"),
		TODO("company"),
		parcel.readValue(Long::class.java.classLoader) as? Long,
		parcel.readValue(Long::class.java.classLoader) as? Long,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(zip)
		parcel.writeString(city)
		parcel.writeString(address1)
		parcel.writeString(lastName)
		parcel.writeValue(jsonMemberDefault)
		parcel.writeString(phone)
		parcel.writeString(name)
		parcel.writeValue(id)
		parcel.writeValue(customerId)
		parcel.writeString(firstName)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<AddressesItem> {
		override fun createFromParcel(parcel: Parcel): AddressesItem {
			return AddressesItem(parcel)
		}

		override fun newArray(size: Int): Array<AddressesItem?> {
			return arrayOfNulls(size)
		}
	}
}
