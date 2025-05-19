package com.nassrallah.vetfarmseller.feature_profile.presentation.personal_info_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.data.dto.CommuneDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.WilayaDTO
import com.nassrallah.vetfarmseller.feature_auth.data.mapper.toDto
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.GetAllWilayasUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.GetCommunesByWilayaUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_profile.domain.use_case.GetSellerByIdUseCase
import com.nassrallah.vetfarmseller.feature_profile.domain.use_case.UpdateSellerUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val getSellerByIdUseCase: GetSellerByIdUseCase,
    private val updateSellerUseCase: UpdateSellerUseCase,
    private val retrieveIdUseCase: RetrieveIdUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase,
    private val getAllWilayasUseCase: GetAllWilayasUseCase,
    private val getCommunesByWilayaUseCase: GetCommunesByWilayaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalInfoScreenState())
    val state: StateFlow<PersonalInfoScreenState> = _state

    private val _form = MutableStateFlow(PersonalInfoFormState())
    val form: StateFlow<PersonalInfoFormState> = _form

    val wilayas = mutableStateOf(
        listOf<WilayaDTO>()
    )

    val communes = mutableStateOf(
        listOf<CommuneDTO>()
    )

    init {
        retrieveId()
        retrieveToken()
        getWilayas()
    }

    fun getSeller() {
        viewModelScope.launch {
            val token = _state.value.token ?: return@launch
            val id = _state.value.sellerId ?: return@launch
            getSellerByIdUseCase(id, token).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            seller = result.data
                        )
                        updateFormInfo()
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

    private fun getWilayas() {
        viewModelScope.launch {
            getAllWilayasUseCase().collect { result ->
                if (result is Resource.Success) {
                    wilayas.value = result.data ?: emptyList()
                }
            }
        }
    }

    fun getCommunes() {
        viewModelScope.launch {
            getCommunesByWilayaUseCase(_state.value.selectedWilayaId).collect { result ->
                if (result is Resource.Success) {
                    communes.value = result.data ?: emptyList()
                }
            }
        }
    }

    private fun retrieveId() {
        viewModelScope.launch {
            retrieveIdUseCase().collect {
                _state.value = _state.value.copy(
                    sellerId = it
                )
            }
        }
    }

    private fun retrieveToken() {
        viewModelScope.launch {
            retrieveTokenUseCase().collect {
                _state.value = _state.value.copy(
                    token = it
                )
            }
        }
    }

    fun updateSeller() {
        viewModelScope.launch {
            val token = _state.value.token ?: return@launch
            val seller = _state.value.seller?.copy(
                businessName = _form.value.businessName,
                wilaya = _form.value.wilaya,
                commune = _form.value.commune,
                address = _form.value.address
            ) ?: return@launch
            updateSellerUseCase(seller.toDto(), token).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            successMessage = result.message
                        )
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

    fun updateBusinessName(value: String) {
        _form.value = _form.value.copy(
            businessName = value
        )
    }

    fun updateWilaya(index: Int) {
        _form.value = _form.value.copy(
            wilaya = wilayas.value[index].ar_name
        )
        _state.value = _state.value.copy(
            selectedWilayaId = wilayas.value[index].id
        )
    }

    fun updateCommune(index: Int) {
        _form.value = _form.value.copy(
            commune = communes.value[index].arName
        )
    }

    fun updateAddress(value: String) {
        _form.value = _form.value.copy(
            address = value
        )
    }

    fun validateInfo(): Boolean {
        return _form.value.businessName.isNotBlank() &&
                _form.value.wilaya.isNotBlank() &&
                _form.value.commune.isNotBlank() &&
                _form.value.address.isNotBlank()
    }

    private fun updateFormInfo() {
        _form.value = _form.value.copy(
            businessName = _state.value.seller?.businessName ?: "",
            wilaya = _state.value.seller?.wilaya ?: "",
            commune = _state.value.seller?.commune ?: "",
            address = _state.value.seller?.address ?: ""
        )
    }

}