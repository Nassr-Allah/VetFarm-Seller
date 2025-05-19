package com.nassrallah.vetfarmseller.feature_profile.data.data_source

import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.utils.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class SellerDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getSellerById(id: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/seller/$id") {
            bearerAuth(token)
        }
    }

    suspend fun getSellerIncome(id: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/seller/$id/income") {
            bearerAuth(token)
        }
    }

    suspend fun updateSeller(sellerDTO: SellerDTO, token: String): HttpResponse {
        return client.put("$BASE_URL/seller") {
            setBody(sellerDTO)
            contentType(ContentType.Application.Json)
            bearerAuth(token)
        }
    }

}