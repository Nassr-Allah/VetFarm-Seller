package com.nassrallah.vetfarmseller.feature_dashboard.presentation.home_screen

import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<ProductDTO> = emptyList(),
    val error: String? = null
)
