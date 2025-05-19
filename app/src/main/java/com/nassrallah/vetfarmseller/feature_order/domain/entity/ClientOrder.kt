package com.nassrallah.vetfarmseller.feature_order.domain.entity

import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderProductDetailsDTO
import kotlinx.datetime.LocalDateTime

data class ClientOrder(
    val id: Int? = null,
    val clientFirstName: String = "",
    val clientLastName: String = "",
    val dateTime: LocalDateTime? = null,
    val deliveryPrice: Float = 0f,
    val price: Float = 0f,
    val status: OrderStatus = OrderStatus.PENDING,
    val address: String = "",
    val products: List<OrderProductDetailsDTO>,
    val transporterFirstName: String,
    val transporterLastName: String,
    val transporterPhoneNumber: String
)
