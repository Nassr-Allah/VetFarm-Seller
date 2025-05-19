package com.nassrallah.vetfarmseller.feature_order.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_order.data.dto.OrderDTO
import com.nassrallah.vetfarmseller.feature_order.domain.repository.OrderRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSellerOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) {

    suspend operator fun invoke(): Flow<Resource<List<OrderDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val id = retrieveIdUseCase().first()
            val token = retrieveTokenUseCase().first()
            val orders = repository.getOrdersBySeller(id, token)
            emit(Resource.Success(orders))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}