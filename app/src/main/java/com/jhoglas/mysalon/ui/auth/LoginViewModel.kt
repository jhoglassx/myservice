package com.jhoglas.mysalon.ui.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName
    var authUIState = mutableStateOf(AuthUIState())

    fun onEvent(event: AuthUIEvent) {
        validateFields()
        when (event) {
            is AuthUIEvent.FirstNameChange -> {
                authUIState.value = authUIState.value.copy(firstName = event.firstName)
                printState()
            }
            is AuthUIEvent.LastNameChange -> {
                authUIState.value = authUIState.value.copy(lastName = event.lastName)
                printState()
            }
            is AuthUIEvent.EmailChange -> {
                authUIState.value = authUIState.value.copy(email = event.email)
                printState()
            }
            is AuthUIEvent.PasswordChange -> {
                authUIState.value = authUIState.value.copy(password = event.password)
                printState()
            }
            is AuthUIEvent.RegisterButtonClick -> {
                register()
            }
        }
    }

    private fun register() {
        Log.d(TAG, "Registering...")
        printState()

        validateFields()
    }

    private fun validateFields() {
        val firstNameResult = Validator.validateFirstName(authUIState.value.firstName)
        val lastNameResult = Validator.validateLastName(authUIState.value.lastName)
        val emailResult = Validator.validateEmail(authUIState.value.email)
        val passwordResult = Validator.validatePassword(authUIState.value.password)
        Log.d(TAG, "ValidateFields:")
        Log.d(TAG, "firstNameResult = $firstNameResult")
        Log.d(TAG, "lastNameResult = $lastNameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")

        authUIState.value = authUIState.value.copy(
            firstNameError = firstNameResult.status,
            lastNameError = lastNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
    }

    private fun printState() {
        Log.d(TAG, "printState: ${authUIState.value}")
    }
}
