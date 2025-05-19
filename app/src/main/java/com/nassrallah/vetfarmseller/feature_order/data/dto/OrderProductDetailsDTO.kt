package com.nassrallah.vetfarmseller.feature_order.data.dto

import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import kotlinx.serialization.Serializable

@Serializable
data class OrderProductDetailsDTO(
    val id: Int? = null,
    val product: ProductDTO,
    var quantityOrdered: Int
)
