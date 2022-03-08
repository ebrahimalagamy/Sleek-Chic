package com.hema.e_commerce.model.dataclass.allProducts
import com.google.gson.annotations.SerializedName
import com.hema.e_commerce.model.dataclass.allProducts.Product


data class ProductItem (
	@SerializedName("product") val product : Product
)