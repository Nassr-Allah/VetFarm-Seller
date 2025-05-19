package com.nassrallah.vetfarmseller.feature_auth.domain.entity

data class User(
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
