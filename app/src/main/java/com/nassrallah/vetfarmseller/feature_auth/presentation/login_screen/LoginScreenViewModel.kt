package com.nassrallah.vetfarmseller.feature_auth.presentation.login_screen

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmseller.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.LoginClientUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginClientUseCase: LoginClientUseCase,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state

    private val _form = MutableStateFlow(LoginFormState())
    val form: StateFlow<LoginFormState> = _form

    init {
        retrieveToken()
    }

    private fun retrieveToken() {
        viewModelScope.launch {
            Log.d("LoginScreenViewModel", "token retrieved")
            retrieveTokenUseCase().collect {
                _state.value = _state.value.copy(
                    token = it
                )
            }
        }
    }

    fun loginClient() {
        viewModelScope.launch {
            val loginCredentials = LoginCredentialsDTO(
                phoneNumber = _form.value.phoneNumber,
                password = _form.value.password
            )
            loginClientUseCase(loginCredentials).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("LoginScreenViewModel", "Loading...")
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("LoginScreenViewModel", "Success: ${result.data}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            loginResponse = result.data
                        )
                    }
                    is Resource.Error -> {
                        Log.d("LoginScreenViewModel", "Error: ${result.message}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updatePhoneNumber(value: String) {
        _form.value = _form.value.copy(
            phoneNumber = value
        )
    }

    fun updatePassword(value: String) {
        _form.value = _form.value.copy(
            password = value
        )
    }

    fun validateInput(): Boolean {
        return _form.value.phoneNumber.isNotBlank() && _form.value.phoneNumber.isDigitsOnly() &&
                _form.value.password.length >= 8
    }

}