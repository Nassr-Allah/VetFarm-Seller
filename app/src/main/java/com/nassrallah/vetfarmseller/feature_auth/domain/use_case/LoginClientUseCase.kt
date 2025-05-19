package com.nassrallah.vetfarmseller.feature_auth.domain.use_case

import android.util.Log
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginResponseDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmseller.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginClientUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(loginCredentials: LoginCredentialsDTO): Flow<Resource<LoginResponseDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepository.loginClient(loginCredentials)
            authRepository.saveClientToken(response.token)
            authRepository.saveClientId(response.id)
            Log.d("LoginClientUseCase", "${response.category}")
            authRepository.saveCategory(response.category)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}