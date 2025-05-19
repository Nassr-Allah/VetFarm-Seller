package com.nassrallah.vetfarmseller.feature_order.data.dto

import com.nassrallah.vetfarmseller.feature_order.domain.entity.OrderStatus
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class OrderDTO(
    val id: Int? = null,
    val datetime: LocalDateTime,
    val price: Float,
    val status: OrderStatus = OrderStatus.PENDING,
    val deliveryPrice: Float,
    val pickupAddress: String,
    val destinationAddress: String,
    val pickupGeolocation: GeoPointDTO,
    val destinationGeolocation: GeoPointDTO,
    val products: List<OrderProductDetailsDTO>,
    val clientID: Int,
    val sellerID: Int
)
