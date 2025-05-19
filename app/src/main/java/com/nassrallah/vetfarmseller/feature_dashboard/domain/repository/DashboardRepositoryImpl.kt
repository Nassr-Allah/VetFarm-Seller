package com.nassrallah.vetfarmseller.feature_dashboard.domain.repository

import com.nassrallah.vetfarmseller.feature_dashboard.data.data_source.DashboardDataSource
import com.nassrallah.vetfarmseller.feature_dashboard.data.dto.SalesInfoDto
import com.nassrallah.vetfarmseller.feature_dashboard.data.repository.DashboardRepository
import com.nassrallah.vetfarmseller.feature_profile.data.data_source.SellerDataSource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val sellerDataSource: SellerDataSource,
    private val dashboardDataSource: DashboardDataSource
) : DashboardRepository {

    override suspend fun getSellerIncome(id: Int, token: String): Float {
        try {
            val response = sellerDataSource.getSellerIncome(id, token)
            if (response.status == HttpStatusCode.OK) {
                val income = response.body<Float>()
                return income
            } else {
                val message = response.body<String>()
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSalesStats(
        from: Int,
        to: Int,
        year: Int,
        sellerId: Int,
        token: String
    ): List<SalesInfoDto> {
        try {
            val response = dashboardDataSource.getSalesStats(from, to, year, sellerId, token)
            if (response.status == HttpStatusCode.OK) {
                val salesStats = response.body<List<SalesInfoDto>>()
                return salesStats
            } else {
                val message = response.body<String>().ifEmpty { "Unknown Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}