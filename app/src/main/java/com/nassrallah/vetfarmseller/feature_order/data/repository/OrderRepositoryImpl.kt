package com.nassrallah.vetfarmseller.feature_order.data.repository

import com.nassrallah.vetfarmseller.feature_order.data.data_source.remote.OrderDataSource
import com.nassrallah.vetfarmseller.feature_order.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.feature_order.domain.repository.OrderRepository
import com.nassrallah.vetfarmseller.utils.Resource
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dataSource: OrderDataSource
) : OrderRepository {

    override suspend fun getOrdersBySeller(sellerId: Int, token: String): List<OrderDTO> {
        try {
            val response = dataSource.getOrdersBySeller(sellerId, token)
            if (response.status.value == 200) {
                val orders = response.body<List<OrderDTO>>()
                return orders
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getOrderClient(clientId: Int, token: String): ClientDTO? {
        var client: ClientDTO? = null
        try {
            val response = dataSource.getOrderClient(clientId, token)
            if (response.status.value == 200) {
                client = response.body()
            }
        } catch (e: Exception) {
            throw e
        }
        return client
    }

    override suspend fun getOrderById(orderId: Int, token: String): Flow<Resource<OrderDTO?>> = flow {
        emit(Resource.Loading())
        try {
            val response = dataSource.getOrderById(orderId, token)
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
}