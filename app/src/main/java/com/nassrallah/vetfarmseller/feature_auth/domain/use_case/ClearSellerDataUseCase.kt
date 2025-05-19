package com.nassrallah.vetfarmseller.feature_auth.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearSellerDataUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            authRepository.clearClientData()
            emit(Resource.Success("Data Cleared"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}