package com.nassrallah.vetfarmseller.feature_inventory.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.UserDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.VetRequestDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.repository.InventoryRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddRequestUseCase @Inject constructor(
    private val repository: InventoryRepository,
    private val retrieveTokenUseCase: RetrieveTokenUseCase,
    private val retrieveIdUseCase: RetrieveIdUseCase
) {

    suspend operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val token = retrieveTokenUseCase().first()
            val id = retrieveIdUseCase().first()
            val successMessage = repository.postVetRequest(id, token)
            emit(Resource.Success(successMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}