package com.nassrallah.vetfarmseller.feature_auth.presentation.professional_info_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.data.dto.CommuneDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.WilayaDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.entity.Category
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.GetAllWilayasUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.GetCommunesByWilayaUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfessionalInfoViewModel @Inject constructor(
    private val getAllWilayasUseCase: GetAllWilayasUseCase,
    private val getCommunesByWilayaUseCase: GetCommunesByWilayaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfessionalInfoScreenState())
    val state: StateFlow<ProfessionalInfoScreenState> = _state

    private val _form = MutableStateFlow(ProfessionalInfoFormState())
    val form: StateFlow<ProfessionalInfoFormState> = _form

    val wilayas = mutableStateOf(
        listOf<WilayaDTO>()
    )
    val communes = mutableStateOf(
        listOf<CommuneDTO>()
    )

    init {
        viewModelScope.launch {
            getWilayas()
        }
    }

    private suspend fun getWilayas() {
        getAllWilayasUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        wilayas = result.data ?: emptyList()
                    )
                    wilayas.value = result.data ?: emptyList()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCommunes() {
        viewModelScope.launch {
            val wilayaId = _state.value.selectedWilaya
            getCommunesByWilayaUseCase(wilayaId).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            communes = result.data ?: emptyList()
                        )
                        communes.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateWilaya(index: Int) {
        _state.value = _state.value.copy(
            selectedWilaya = wilayas.value[index].id
        )
        _form.value = _form.value.copy(
            wilaya = wilayas.value[index].ar_name
        )
    }

    fun updateCommune(index: Int) {
        _form.value = _form.value.copy(
            commune = communes.value[index].arName
        )
    }

    fun updateCategory(category: Category) {
        _state.value = _state.value.copy(
            selectedCategory = category
        )
        _form.value = _form.value.copy(
            category = category.name
        )
    }

    fun updateBusinessName(value: String) {
        _form.value = _form.value.copy(
            businessName = value
        )
    }

    fun updateAddress(value: String) {
        _form.value = _form.value.copy(
            address = value
        )
    }

    fun validateInput(): Boolean {
        return _form.value.businessName.isNotBlank() &&
                _form.value.wilaya.isNotBlank() &&
                _form.value.commune.isNotBlank() &&
                _form.value.address.isNotBlank() &&
                _form.value.category.isNotBlank()
    }

}