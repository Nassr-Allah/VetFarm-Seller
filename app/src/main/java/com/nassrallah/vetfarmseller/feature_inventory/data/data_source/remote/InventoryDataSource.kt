package com.nassrallah.vetfarmseller.feature_inventory.data.data_source.remote

import com.nassrallah.vetfarmseller.feature_inventory.data.dto.ProductDTO
import com.nassrallah.vetfarmseller.utils.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class InventoryDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getSellerProducts(sellerId: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/browse/sellers/products/$sellerId") {
            bearerAuth(token)
        }
    }

    suspend fun getProductById(id: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/browse/product/$id") {
            bearerAuth(token)
        }
    }

    suspend fun insertProduct(product: ProductDTO, token: String): HttpResponse {
        return client.post("$BASE_URL/browse/product") {
            contentType(ContentType.Application.Json)
            setBody(product)
            bearerAuth(token)
        }
    }

}