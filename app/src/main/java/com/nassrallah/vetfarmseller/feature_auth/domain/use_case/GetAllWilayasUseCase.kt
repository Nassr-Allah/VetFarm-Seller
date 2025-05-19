package com.nassrallah.vetfarmseller.feature_auth.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.data.dto.WilayaDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllWilayasUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<WilayaDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val wilayas = authRepository.getAllWilayas()
            emit(Resource.Success(wilayas))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}