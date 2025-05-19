package com.nassrallah.vetfarmseller.feature_auth.presentation.otp_screen

data class OtpScreenState(
    val isLoading: Boolean = false,
    val code: String = "",
    val phoneNumber: String = "",
    val error: String? = null
)
