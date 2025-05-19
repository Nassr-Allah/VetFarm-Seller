package com.nassrallah.vetfarmseller.feature_auth.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.data.dto.CommuneDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCommunesByWilayaUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(wilayaId: String): Flow<Resource<List<CommuneDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val communes = authRepository.getCommunesByWilaya(wilayaId)
            emit(Resource.Success(communes))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}