package com.nassrallah.vetfarmseller.feature_auth.presentation.login_screen

import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginResponseDTO

data class LoginScreenState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponseDTO? = null,
    val token: String = "",
    val error: String? = null
)
