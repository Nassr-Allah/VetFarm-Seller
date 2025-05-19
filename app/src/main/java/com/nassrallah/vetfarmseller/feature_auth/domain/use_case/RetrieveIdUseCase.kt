package com.nassrallah.vetfarmseller.feature_auth.domain.use_case

import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class RetrieveIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = authRepository.retrieveClientId()

}