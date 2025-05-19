package com.nassrallah.vetfarmseller.feature_profile.data.repository

import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_profile.data.data_source.SellerDataSource
import com.nassrallah.vetfarmseller.feature_profile.domain.repository.ProfileRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: SellerDataSource
) : ProfileRepository {

    override suspend fun getSellerById(id: Int, token: String): SellerDTO {
        try {
            val response = dataSource.getSellerById(id, token)
            if (response.status == HttpStatusCode.OK) {
                val seller = response.body<SellerDTO>()
                return seller
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateSeller(seller: SellerDTO, token: String): String {
        try {
            val response = dataSource.updateSeller(seller, token)
            if (response.status == HttpStatusCode.OK) {
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
}