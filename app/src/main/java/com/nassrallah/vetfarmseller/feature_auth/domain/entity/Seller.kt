package com.nassrallah.vetfarmseller.feature_auth.domain.entity

import com.nassrallah.vetfarmseller.feature_auth.data.dto.UserDTO
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO

data class Seller(
    val id: Int? = null,
    val user: UserDTO,
    val businessName: String,
    val wilaya: String,
    val commune: String,
    val address: String,
    val category: Category,
)
