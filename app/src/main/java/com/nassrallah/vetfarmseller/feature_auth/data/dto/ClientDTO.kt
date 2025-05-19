package com.nassrallah.vetfarmseller.feature_auth.data.dto

import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import kotlinx.serialization.Serializable

@Serializable
data class ClientDTO(
    val id: Int? = null,
    val user: UserDTO,
    val businessName: String,
    val wilaya: String,
    val commune: String,
    val address: String,
    val category: Category
)
