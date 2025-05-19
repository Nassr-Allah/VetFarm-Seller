package com.nassrallah.vetfarmseller.feature_profile.domain.repository

import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO

interface ProfileRepository {

    suspend fun getSellerById(id: Int, token: String): SellerDTO

    suspend fun updateSeller(seller: SellerDTO, token: String): String

}