package com.hema.e_commerce.model.dataclass.customer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Address(

    @SerializedName( "id")
    val id: Long? = 0,

    @SerializedName( "address1")
    val address: String? = "",

    @SerializedName( "city")
    val city: String? = "",

    @SerializedName( "first_name")
    val firstName: String? = "",

    @SerializedName( "last_name")
    val lastName: String? = "",

    @SerializedName( "zip")
    val zip: String? = "",

    @SerializedName( "default")
    val default: Boolean = false,

    @SerializedName( "phone")
    val phone: String? = "",

): Parcelable {
    fun generateAddressLine():String{
        return "$city, $address"
    }


}
