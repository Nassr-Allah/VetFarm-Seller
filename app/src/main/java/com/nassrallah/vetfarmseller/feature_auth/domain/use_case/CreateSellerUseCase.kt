package com.nassrallah.vetfarmseller.feature_auth.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateSellerUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(seller: SellerDTO): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val successMessage = authRepository.createSeller(seller)
            emit(Resource.Success(successMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}