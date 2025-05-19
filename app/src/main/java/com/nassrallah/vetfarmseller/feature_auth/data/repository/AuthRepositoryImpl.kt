package com.nassrallah.vetfarmseller.feature_auth.data.repository

import com.nassrallah.vetfarmseller.feature_auth.data.data_source.AuthDataSource
import com.nassrallah.vetfarmseller.feature_auth.data.data_source.CommuneDataSource
import com.nassrallah.vetfarmseller.feature_auth.data.data_source.WilayaDataSource
import com.nassrallah.vetfarmseller.feature_auth.data.data_source.local.AppDataStore
import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.CommuneDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginResponseDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.WilayaDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val appDataStore: AppDataStore,
    private val wilayaDataSource: WilayaDataSource,
    private val communeDataSource: CommuneDataSource
) : AuthRepository {

    override suspend fun loginClient(loginCredentials: LoginCredentialsDTO): LoginResponseDTO {
        try {
            val response = authDataSource.loginSeller(loginCredentials)
            if (response.status == HttpStatusCode.Accepted) {
                val loginResponse = response.body<LoginResponseDTO>()
                return loginResponse
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createClient(client: ClientDTO): String {
        try {
            val response = authDataSource.createClient(client)
            if (response.status == HttpStatusCode.Created) {
                val successMessage = response.body<String>()
                return successMessage
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createSeller(seller: SellerDTO): String {
        try {
            val response = authDataSource.createSeller(seller)
            if (response.status == HttpStatusCode.Created) {
                val successMessage = response.body<String>()
                return successMessage
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun retrieveClientToken(): Flow<String> = appDataStore.token

    override suspend fun saveClientToken(token: String) {
        appDataStore.saveToken(token)
    }

    override suspend fun retrieveClientId(): Flow<Int> = appDataStore.id

    override suspend fun saveClientId(id: Int) {
        appDataStore.saveId(id)
    }

    override suspend fun saveCategory(category: Category) {
        appDataStore.saveCategory(category.name)
    }

    override suspend fun retrieveCategory(): Flow<String> = appDataStore.category

    override suspend fun clearClientData() {
        appDataStore.clearData()
    }

    override suspend fun getAllWilayas(): List<WilayaDTO> {
        try {
            val response = wilayaDataSource.getAllWilayas()
            if (response.status == HttpStatusCode.OK) {
                val wilayas = response.body<List<WilayaDTO>>()
                return wilayas
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCommunesByWilaya(wilayaId: String): List<CommuneDTO> {
        try {
            val response = communeDataSource.getCommunesByWilaya(wilayaId)
            if (response.status == HttpStatusCode.OK) {
                val communes = response.body<List<CommuneDTO>>()
                return communes
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}