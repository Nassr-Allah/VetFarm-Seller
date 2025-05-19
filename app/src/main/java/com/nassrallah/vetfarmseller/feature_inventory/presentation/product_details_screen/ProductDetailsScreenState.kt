package com.nassrallah.vetfarmseller.feature_inventory.presentation.product_details_screen

import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO

data class ProductDetailsScreenState(
    val data: ProductDTO? = null,
    val response: String? = null,
    val sellerId: Int? = null,
    val token: String? = null,
    val category: Category? = null,
    val isLoading: Boolean = false,
    val isRequestAdded: Boolean = false,
    val error: String? = null
)
