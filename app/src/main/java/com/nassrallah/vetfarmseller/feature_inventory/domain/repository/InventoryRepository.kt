package com.nassrallah.vetfarmseller.feature_inventory.domain.repository

import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {

    suspend fun getSellerProducts(sellerId: Int, token: String): List<ProductDTO>

    suspend fun getProductDetails(id: Int, token: String): Flow<Resource<ProductDTO>>

    suspend fun insertProduct(product: ProductDTO, token: String): Flow<Resource<String>>

    suspend fun postVetRequest(sellerId: Int, token: String): String

}