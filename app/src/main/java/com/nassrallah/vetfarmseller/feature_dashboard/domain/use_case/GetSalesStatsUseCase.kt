package com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.data.dto.SalesInfoDto
import com.nassrallah.vetfarmseller.feature_dashboard.data.repository.DashboardRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSalesStatsUseCase @Inject constructor(
    private val repository: DashboardRepository,
    private val retrieveTokenUseCase: RetrieveTokenUseCase,
    private val retrieveIdUseCase: RetrieveIdUseCase
) {

    suspend operator fun invoke(from: Int, to: Int, year: Int): Flow<Resource<List<SalesInfoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val token = retrieveTokenUseCase().first()
            val sellerId = retrieveIdUseCase().first()
            val salesStats = repository.getSalesStats(from, to, year, sellerId, token).sortedBy { it.month }
            emit(Resource.Success(salesStats))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}