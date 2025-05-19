package com.nassrallah.vetfarmseller.feature_auth.data.mapper

import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Seller

fun SellerDTO.toSeller(): Seller {
    return Seller(id, user, businessName, wilaya, commune, address, category)
}

fun Seller.toDto(): SellerDTO {
    return SellerDTO(id, user, businessName, wilaya, commune, address, category)
}