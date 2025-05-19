package com.nassrallah.vetfarmseller.feature_dashboard.data.repository

import com.nassrallah.vetfarmseller.feature_dashboard.data.dto.SalesInfoDto

interface DashboardRepository {

    suspend fun getSellerIncome(id: Int, token: String): Float

    suspend fun getSalesStats(from: Int, to: Int, year: Int, sellerId: Int, token: String): List<SalesInfoDto>

}