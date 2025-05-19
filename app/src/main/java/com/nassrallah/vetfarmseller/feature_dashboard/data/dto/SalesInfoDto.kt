package com.nassrallah.vetfarmseller.feature_dashboard.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SalesInfoDto(
    val ordersNumber: Int,
    val month: Int,
    val year: Int
)
