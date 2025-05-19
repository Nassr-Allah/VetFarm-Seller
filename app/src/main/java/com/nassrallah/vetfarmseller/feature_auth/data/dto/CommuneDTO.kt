package com.nassrallah.vetfarmseller.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommuneDTO(
    val id: String,
    val arName: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val postCode: String,
    val wilayaId: String
)