package com.nassrallah.vetfarmseller.feature_order.presentation.orders_screen

import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder

data class OrdersScreenState(
    val data: List<ClientOrder> = emptyList(),
    val sellerId: Int? = null,
    val token: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
