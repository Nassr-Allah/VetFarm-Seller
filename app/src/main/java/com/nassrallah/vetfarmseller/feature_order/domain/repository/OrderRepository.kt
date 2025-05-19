package com.nassrallah.vetfarmseller.feature_order.domain.repository

import com.nassrallah.vetfarmseller.feature_order.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun getOrdersBySeller(sellerId: Int, token: String): List<OrderDTO>

    suspend fun getOrderClient(clientId: Int, token: String): ClientDTO?

    suspend fun getOrderById(orderId: Int, token: String): Flow<Resource<OrderDTO?>>

}