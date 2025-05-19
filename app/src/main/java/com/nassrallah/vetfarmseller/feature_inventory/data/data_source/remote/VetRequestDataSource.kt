package com.nassrallah.vetfarmseller.feature_inventory.data.data_source.remote

import com.nassrallah.vetfarmseller.feature_inventory.data.dto.VetRequestDTO
import com.nassrallah.vetfarmseller.utils.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class VetRequestDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun postVetRequest(sellerId: Int, token: String): HttpResponse {
        return httpClient.post("$BASE_URL/vet-request") {
            parameter("sellerId", sellerId)
            bearerAuth(token)
        }
    }

}