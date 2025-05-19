package com.nassrallah.vetfarmseller.feature_profile.presentation.profile_screen

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String? = null
)
