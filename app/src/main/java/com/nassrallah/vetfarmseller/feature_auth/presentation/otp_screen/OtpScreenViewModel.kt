package com.nassrallah.vetfarmseller.feature_auth.presentation.otp_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OtpScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(OtpScreenState())
    val state: StateFlow<OtpScreenState> = _state

    private val _smsState = MutableStateFlow(OtpScreenSmsState())
    val smsState: StateFlow<OtpScreenSmsState> = _smsState

    fun updatePhoneNumber(value: String) {
        _state.value = _state.value.copy(
            phoneNumber = value
        )
    }

    fun updateDigit(index: Int, value: String) {
        when(index) {
            0 -> {
                _smsState.value = _smsState.value.copy(
                    digit1 = value
                )
            }
            1 -> {
                _smsState.value = _smsState.value.copy(
                    digit2 = value
                )
            }
            2 -> {
                _smsState.value = _smsState.value.copy(
                    digit3 = value
                )
            }
            3 -> {
                _smsState.value = _smsState.value.copy(
                    digit4 = value
                )
            }
            4 -> {
                _smsState.value = _smsState.value.copy(
                    digit5 = value
                )
            }
            5 -> {
                _smsState.value = _smsState.value.copy(
                    digit6 = value
                )
            }
        }
    }

}