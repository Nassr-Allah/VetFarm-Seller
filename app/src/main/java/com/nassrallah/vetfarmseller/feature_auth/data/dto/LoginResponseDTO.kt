package com.nassrallah.vetfarmseller.feature_auth.data.dto

import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val token: String,
    val id: Int,
    val category: Category
)
