package com.nassrallah.vetfarmseller.feature_dashboard.presentation.dashboard_screen

import com.nassrallah.vetfarmseller.feature_dashboard.data.dto.SalesInfoDto
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO

data class DashboardScreenState(
    val isLoading: Boolean = false,
    val salesStats: List<SalesInfoDto> = emptyList(),
    val income: Float = 0f,
    val ordersCount: Int = 0,
    val topSeller: String = "",
    val products: List<ProductDTO> = emptyList(),
    val error: String? = null
)
