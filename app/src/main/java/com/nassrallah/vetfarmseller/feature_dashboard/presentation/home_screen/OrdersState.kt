package com.nassrallah.vetfarmseller.feature_dashboard.presentation.home_screen

import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO

data class OrdersState(
    val isLoading: Boolean = false,
    val orders: List<OrderDTO> = emptyList(),
    val error: String? = null
)
