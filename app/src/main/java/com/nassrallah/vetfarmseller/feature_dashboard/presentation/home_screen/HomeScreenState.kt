package com.nassrallah.vetfarmseller.feature_dashboard.presentation.home_screen

import com.nassrallah.vetfarmseller.feature_dashboard.domain.entity.OrdersCount
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO

data class HomeScreenState(
    val ordersState: OrdersState = OrdersState(),
    val productsState: ProductsState = ProductsState(),
    val dashboardState: DashboardState = DashboardState(),
    val sellerId: Int? = null
)
