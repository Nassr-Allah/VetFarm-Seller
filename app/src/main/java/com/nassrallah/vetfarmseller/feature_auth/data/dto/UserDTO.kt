package com.nassrallah.vetfarmseller.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String? = null,
    val isApproved: Boolean = false,
    val lat: Double = 35.0,
    val lng: Double = 35.0
)