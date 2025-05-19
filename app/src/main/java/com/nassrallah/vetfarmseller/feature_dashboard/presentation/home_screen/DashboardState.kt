package com.nassrallah.vetfarmseller.feature_dashboard.presentation.home_screen

import com.nassrallah.vetfarmseller.feature_dashboard.domain.entity.OrdersCount

data class DashboardState(
    val isLoading: Boolean = false,
    val ordersCount: OrdersCount = OrdersCount(),
    val income: Float = 0f,
    val error: String? = null
)
