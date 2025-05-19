package com.nassrallah.vetfarmseller.feature_inventory.data.dto

import com.nassrallah.vetfarmseller.feature_auth.data.dto.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class VetDTO(
    val id: Int? = null,
    val user: UserDTO,
    val agreementNumber: String,
    val avnNumber: String
)
