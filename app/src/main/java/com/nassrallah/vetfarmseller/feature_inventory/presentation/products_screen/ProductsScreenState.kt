package com.nassrallah.vetfarmseller.feature_inventory.presentation.products_screen

import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO

data class ProductsScreenState(
    val data: List<ProductDTO>? = null,
    val sellerId: Int? = null,
    val token: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
