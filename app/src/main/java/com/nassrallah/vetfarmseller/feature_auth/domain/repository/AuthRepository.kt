package com.nassrallah.vetfarmseller.feature_auth.domain.repository

import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.CommuneDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginResponseDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.WilayaDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun loginClient(loginCredentials: LoginCredentialsDTO): LoginResponseDTO

    suspend fun createClient(client: ClientDTO): String

    suspend fun createSeller(seller: SellerDTO): String

    suspend fun retrieveClientToken(): Flow<String>

    suspend fun saveClientToken(token: String)

    suspend fun retrieveClientId(): Flow<Int>

    suspend fun saveClientId(id: Int)

    suspend fun saveCategory(category: Category)

    suspend fun retrieveCategory(): Flow<String>

    suspend fun clearClientData()

    suspend fun getAllWilayas(): List<WilayaDTO>

    suspend fun getCommunesByWilaya(wilayaId: String): List<CommuneDTO>

}