package com.nassrallah.vetfarmseller.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentialsDTO(
    val phoneNumber: String,
    val password: String
)
