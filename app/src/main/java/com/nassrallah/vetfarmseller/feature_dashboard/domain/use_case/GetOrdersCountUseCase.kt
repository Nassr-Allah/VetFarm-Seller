package com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.domain.entity.OrdersCount
import com.nassrallah.vetfarmseller.feature_order.domain.entity.OrderStatus
import com.nassrallah.vetfarmseller.feature_order.domain.repository.OrderRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrdersCountUseCase @Inject constructor(
    private val repository: OrderRepository,
    private val retrieveTokenUseCase: RetrieveTokenUseCase,
    private val retrieveIdUseCase: RetrieveIdUseCase
) {

    suspend operator fun invoke(): Flow<Resource<OrdersCount>> = flow {
        try {
            emit(Resource.Loading())
            val sellerId = retrieveIdUseCase().first()
            val sellerToken = retrieveTokenUseCase().first()
            val orders = repository.getOrdersBySeller(sellerId, sellerToken)
            val completedOrders = orders.count { it.status == OrderStatus.COMPLETED }
            val pendingOrders = orders.count { it.status == OrderStatus.PENDING }
            val ordersCount = OrdersCount(
                completed = completedOrders,
                pending = pendingOrders
            )
            emit(Resource.Success(ordersCount))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}