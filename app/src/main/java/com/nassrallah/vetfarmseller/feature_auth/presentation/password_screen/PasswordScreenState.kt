package com.nassrallah.vetfarmseller.feature_auth.presentation.password_screen

import android.location.Location

data class PasswordScreenState(
    val isLoading: Boolean = false,
    val isAccountCreated: Boolean = false,
    val location: Location? = null,
    val error: String? = null
)
