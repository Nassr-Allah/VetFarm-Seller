package com.nassrallah.vetfarmseller.feature_auth.presentation.signup_screen

import android.util.Patterns
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignupScreenViewModel @Inject constructor() : ViewModel() {

    private val _form = MutableStateFlow(SignupFormState())
    val form: StateFlow<SignupFormState> = _form

    fun updateFirstName(value: String) {
        _form.value = _form.value.copy(
            firstName = value
        )
    }

    fun updateLastName(value: String) {
        _form.value = _form.value.copy(
            lastName = value
        )
    }

    fun updateEmail(value: String) {
        _form.value = _form.value.copy(
            email = value
        )
    }

    fun updateAge(value: String) {
        _form.value = _form.value.copy(
            age = value
        )
    }

    fun validateInput(): Boolean {
        return _form.value.firstName.isNotBlank() &&
                _form.value.lastName.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(_form.value.email).matches() &&
                _form.value.age.isDigitsOnly()
    }

}