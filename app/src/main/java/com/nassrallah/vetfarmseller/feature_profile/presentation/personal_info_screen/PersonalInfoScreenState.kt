package com.nassrallah.vetfarmseller.feature_profile.presentation.personal_info_screen

import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Seller

data class PersonalInfoScreenState(
    val isLoading: Boolean = false,
    val sellerId: Int? = null,
    val token: String? = null,
    val seller: Seller? = null,
    val successMessage: String? = null,
    val selectedWilayaId: String = "",
    val error: String? = null
)
