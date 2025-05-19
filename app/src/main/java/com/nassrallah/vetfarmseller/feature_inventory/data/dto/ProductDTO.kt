package com.nassrallah.vetfarmseller.feature_inventory.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: Int? = null,
    val name: String,
    val price: Float,
    val sellerID: Int,
    val quantity: Int,
    val quantitySold: Int = 0,
    val income: Float = 0.0f
)