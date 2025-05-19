package com.nassrallah.vetfarmseller.feature_order.domain.use_case

import android.util.Log
import com.nassrallah.vetfarmseller.feature_order.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.feature_order.data.mapper.createClientOrder
import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder
import com.nassrallah.vetfarmseller.feature_order.domain.repository.OrderRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    suspend operator fun invoke(sellerId: Int, token: String): Flow<Resource<List<ClientOrder>>> = flow {
        emit(Resource.Loading())
        val orderFlow = flowOf(orderRepository.getOrdersBySeller(sellerId, token))
        val clientOrders = mutableListOf<ClientOrder>()
        val resultFlow = orderFlow.flatMapConcat { orders ->
            Log.d("GetOrdersUseCase", "$orders")
            flow {
                emit(Resource.Loading())
                orders.forEach {
                    //TODO: this should be cleaned
                    val client = orderRepository.getOrderClient(it.clientID, token)
                    val clientOrder = createClientOrder(it, client!!)
                    clientOrders.add(clientOrder)
                }
                emit(Resource.Success(clientOrders))
            }
        }
        resultFlow.collect {
           when(it) {
               is Resource.Loading -> emit(Resource.Loading())
               is Resource.Success -> emit(Resource.Success(it.data))
               is Resource.Error -> emit(Resource.Error(it.message))
           }
        }
    }

}