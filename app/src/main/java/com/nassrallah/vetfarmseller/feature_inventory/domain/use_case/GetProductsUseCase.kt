package com.nassrallah.vetfarmseller.feature_inventory.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.feature_inventory.domain.repository.InventoryRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: InventoryRepository,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) {

    suspend operator fun invoke(): Flow<Resource<List<ProductDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val sellerToken = retrieveTokenUseCase().first()
            val sellerId = retrieveIdUseCase().first()
            val products = repository.getSellerProducts(sellerId, sellerToken)
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}