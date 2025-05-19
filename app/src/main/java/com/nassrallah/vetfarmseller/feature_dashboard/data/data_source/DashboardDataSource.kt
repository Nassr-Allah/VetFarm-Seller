package com.nassrallah.vetfarmseller.feature_dashboard.data.data_source

import com.nassrallah.vetfarmseller.utils.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class DashboardDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getSalesStats(from: Int, to: Int, year: Int, sellerId: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/dashboard/$sellerId/sales-stats") {
            parameter("from", from)
            parameter("to", to)
            parameter("year", year)
            bearerAuth(token)
        }
    }

}