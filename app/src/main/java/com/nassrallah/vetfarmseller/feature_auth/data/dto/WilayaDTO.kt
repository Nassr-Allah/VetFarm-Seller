package com.nassrallah.vetfarmseller.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WilayaDTO(
    val ar_name: String,
    val code: String,
    val id: String,
    val latitude: String,
    val longitude: String,
    val name: String
)
