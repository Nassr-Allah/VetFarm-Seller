package com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.data.repository.DashboardRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSellerIncomeUseCase @Inject constructor(
    private val repository: DashboardRepository,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) {

    suspend operator fun invoke(): Flow<Resource<Float>> = flow {
        try {
            emit(Resource.Loading())
            val sellerId = retrieveIdUseCase().first()
            val sellerToken = retrieveTokenUseCase().first()
            val income = repository.getSellerIncome(sellerId, sellerToken)
            emit(Resource.Success(income))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}