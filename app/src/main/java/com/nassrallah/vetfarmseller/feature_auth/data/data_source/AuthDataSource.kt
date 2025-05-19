package com.nassrallah.vetfarmseller.feature_auth.data.data_source

import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.utils.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun loginSeller(loginCredentials: LoginCredentialsDTO): HttpResponse {
        return httpClient.post("$BASE_URL/auth/login/seller") {
            setBody(loginCredentials)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun createClient(client: ClientDTO): HttpResponse {
        return httpClient.post("$BASE_URL/register/client") {
            setBody(client)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun createSeller(seller: SellerDTO): HttpResponse {
        return httpClient.post("$BASE_URL/register/seller") {
            setBody(seller)
            contentType(ContentType.Application.Json)
        }
    }

}