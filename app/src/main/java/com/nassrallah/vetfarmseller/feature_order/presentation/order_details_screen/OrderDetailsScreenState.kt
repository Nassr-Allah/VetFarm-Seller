package com.nassrallah.vetfarmseller.feature_order.presentation.order_details_screen

import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder

data class OrderDetailsScreenState(
    val data: ClientOrder? = null,
    val isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)
