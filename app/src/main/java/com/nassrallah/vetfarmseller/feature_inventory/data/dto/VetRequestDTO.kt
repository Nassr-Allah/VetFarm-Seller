package com.nassrallah.vetfarmseller.feature_inventory.data.dto

import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.entity.RequestType
import kotlinx.serialization.Serializable

@Serializable
data class VetRequestDTO(
    val id: Int? = null,
    val type: RequestType = RequestType.CONSULTATION,
    val seller: SellerDTO,
    val vet: VetDTO? = null
)
