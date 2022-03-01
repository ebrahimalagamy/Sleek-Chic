package com.hema.e_commerce.model.dataclass.getOrder

data class TotalShippingPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
)