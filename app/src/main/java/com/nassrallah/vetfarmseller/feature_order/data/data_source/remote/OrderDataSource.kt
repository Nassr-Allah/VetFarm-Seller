package com.nassrallah.vetfarmseller.feature_order.data.data_source.remote

import com.nassrallah.vetfarmseller.utils.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class OrderDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getOrdersBySeller(sellerId: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/order/seller/$sellerId") {
            bearerAuth(token)
        }
    }

    suspend fun getOrderClient(clientId: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/client/$clientId") {
            bearerAuth(token)
        }
    }

    suspend fun getOrderById(orderId: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/order/$orderId") {
            bearerAuth(token)
        }
    }

}