package com.nassrallah.vetfarmseller.feature_auth.presentation.professional_info_screen

import com.nassrallah.vetfarmseller.feature_auth.data.dto.CommuneDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.WilayaDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category

data class ProfessionalInfoScreenState(
    val isLoading: Boolean = false,
    val wilayas: List<WilayaDTO> = emptyList(),
    val communes: List<CommuneDTO> = emptyList(),
    val selectedWilaya: String = "",
    val selectedCommune: String = "",
    val selectedCategory: Category = Category.ABATTOIRS,
    val error: String? = null
)
