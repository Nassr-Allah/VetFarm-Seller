package com.nassrallah.vetfarmseller.feature_auth.data.data_source

import com.nassrallah.vetfarmseller.utils.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class WilayaDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getAllWilayas(): HttpResponse {
        return httpClient.get("$BASE_URL/wilaya")
    }

}