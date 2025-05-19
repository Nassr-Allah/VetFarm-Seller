package com.nassrallah.vetfarmseller.feature_inventory.data.repository

import com.nassrallah.vetfarmseller.feature_inventory.data.data_source.remote.InventoryDataSource
import com.nassrallah.vetfarmseller.feature_inventory.data.data_source.remote.VetRequestDataSource
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.repository.InventoryRepository
import com.nassrallah.vetfarmseller.utils.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val inventoryDataSource: InventoryDataSource,
    private val vetRequestDataSource: VetRequestDataSource
): InventoryRepository {

    override suspend fun getSellerProducts(sellerId: Int, token: String): List<ProductDTO> {
        try {
            val response = inventoryDataSource.getSellerProducts(sellerId, token)
            if (response.status == HttpStatusCode.OK) {
                val products = response.body<List<ProductDTO>>()
                return products
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProductDetails(id: Int, token: String): Flow<Resource<ProductDTO>> = flow {
        emit(Resource.Loading())
        try {
            val response = inventoryDataSource.getProductById(id, token)
            if (response.status.value == 200) {
                emit(Resource.Success(response.body()))
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                emit(Resource.Error(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

    override suspend fun insertProduct(product: ProductDTO, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = inventoryDataSource.insertProduct(product, token)
            if (response.status.value == 201) {
                emit(Resource.Success(response.body()))
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                emit(Resource.Error(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

    override suspend fun postVetRequest(sellerId: Int, token: String): String {
        try {
            val response = vetRequestDataSource.postVetRequest(sellerId, token)
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
}