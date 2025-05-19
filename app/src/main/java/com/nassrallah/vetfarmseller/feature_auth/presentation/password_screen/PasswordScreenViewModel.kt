package com.nassrallah.vetfarmseller.feature_auth.presentation.password_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.data.dto.ClientDTO
import com.nassrallah.vetfarmseller.feature_auth.data.dto.SellerDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.CreateClientUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.CreateSellerUseCase
import com.nassrallah.vetfarmseller.location.LocationClient
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordScreenViewModel @Inject constructor(
    private val createSellerUseCase: CreateSellerUseCase,
    private val locationClient: LocationClient
) : ViewModel() {

    private val _state = MutableStateFlow(PasswordScreenState())
    val state: StateFlow<PasswordScreenState> = _state

    private val _form = MutableStateFlow(PasswordScreenForm())
    val form: StateFlow<PasswordScreenForm> = _form

    init {
        getLocation()
    }

    private fun getLocation() {
        viewModelScope.launch {
            locationClient.getLastKnownLocation().collect {
                _state.value = _state.value.copy(
                    location = it
                )
            }
        }
    }

    fun createSeller(seller: SellerDTO) {
        viewModelScope.launch {
            val location = _state.value.location ?: return@launch
            val sellerCopy = seller.copy(
                user = seller.user.copy(
                    password = _form.value.password,
                    lat = location.latitude,
                    lng = location.longitude
                )
            )
            createSellerUseCase(sellerCopy).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("PasswordScreenVM", "Loading...")
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("PasswordScreenVM", "Success: ${result.data}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isAccountCreated = true
                        )
                    }
                    is Resource.Error -> {
                        Log.d("PasswordScreenVM", "Error: ${result.message}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updatePassword(value: String) {
        _form.value = _form.value.copy(
            password = value
        )
    }

    fun updateConfirmPassword(value: String) {
        _form.value = _form.value.copy(
            confirmPassword = value
        )
    }

    fun validateInput(): Boolean {
        return _form.value.password.length >= 8 &&
                _form.value.password == _form.value.confirmPassword
    }

}