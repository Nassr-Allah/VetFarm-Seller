package com.nassrallah.vetfarmseller.feature_profile.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.data.mapper.toClient
import com.nassrallah.vetfarmseller.feature_auth.data.mapper.toSeller
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Client
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Seller
import com.nassrallah.vetfarmseller.feature_profile.domain.repository.ProfileRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSellerByIdUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(id: Int, token: String): Flow<Resource<Seller>> = flow {
        try {
            emit(Resource.Loading())
            val seller = repository.getSellerById(id, token).toSeller()
            emit(Resource.Success(seller))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}