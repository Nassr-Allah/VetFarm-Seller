package com.nassrallah.vetfarmseller.feature_profile.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_profile.domain.repository.ProfileRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateSellerUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(seller: SellerDTO, token: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val successMessage = repository.updateSeller(seller, token)
            emit(Resource.Success(successMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}