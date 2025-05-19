package com.nassrallah.vetfarmseller.feature_order.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeoPointDTO(
    val id: Int? = null,
    val lat: Double,
    val lng: Double
)
