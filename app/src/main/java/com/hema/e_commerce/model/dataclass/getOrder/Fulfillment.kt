package com.hema.e_commerce.model.dataclass.getOrder

data class Fulfillment(
    val admin_graphql_api_id: String,
    val created_at: String,
    val id: Int,
    val line_items: List<LineItem>,
    val location_id: Int,
    val name: String,
    val order_id: Int,
    val receipt: Receipt,
    val service: String,
    val shipment_status: Any,
    val status: String,
    val tracking_company: String,
    val tracking_number: String,
    val tracking_numbers: List<String>,
    val tracking_url: String,
    val tracking_urls: List<String>,
    val updated_at: String
)