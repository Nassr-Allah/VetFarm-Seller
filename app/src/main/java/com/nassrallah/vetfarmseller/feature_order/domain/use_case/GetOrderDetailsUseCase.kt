package com.nassrallah.vetfarmseller.feature_order.domain.use_case

import com.nassrallah.vetfarmseller.feature_order.data.mapper.createClientOrder
import com.nassrallah.vetfarmseller.feature_order.domain.entity.ClientOrder
import com.nassrallah.vetfarmseller.feature_order.domain.repository.OrderRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    suspend operator fun invoke(orderId: Int, token: String): Flow<Resource<ClientOrder>> = flow {
        emit(Resource.Loading())
        val orderFlow = orderRepository.getOrderById(orderId, token)
        val resultFlow = orderFlow
            .flatMapConcat { result ->
                flow {
                    if (result is Resource.Success) {
                        val clientId = result.data?.clientID ?: return@flow
                        val client = orderRepository.getOrderClient(clientId, token) ?: return@flow
                        val clientOrder = createClientOrder(result.data, client)
                        emit(Resource.Success(clientOrder))
                    } else {
                        emit(Resource.Error(result.message))
                    }
                }
            }.catch {
                emit(Resource.Error(it.localizedMessage))
            }

        resultFlow.collect { emit(it) }
    }

}