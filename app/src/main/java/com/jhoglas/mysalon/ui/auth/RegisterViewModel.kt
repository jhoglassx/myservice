package com.jhoglas.mysalon.ui.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen

class RegisterViewModel : ViewModel() {

    private val TAG = RegisterViewModel::class.simpleName
    var registerUIState = mutableStateOf(RegisterUIState())
    val allValidationsPassed = mutableStateOf(false)
    val registerInProgress = mutableStateOf(false)

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.FirstNameChange -> {
                registerUIState.value = registerUIState.value.copy(firstName = event.firstName)
                printState()
            }
            is RegisterUIEvent.LastNameChange -> {
                registerUIState.value = registerUIState.value.copy(lastName = event.lastName)
                printState()
            }
            is RegisterUIEvent.EmailChange -> {
                registerUIState.value = registerUIState.value.copy(email = event.email)
                printState()
            }
            is RegisterUIEvent.PasswordChange -> {
                registerUIState.value = registerUIState.value.copy(password = event.password)
                printState()
            }
            is RegisterUIEvent.RegisterButtonClick -> {
                register()
            }
            is RegisterUIEvent.PrivacyPolicyCheckChange -> {
                registerUIState.value = registerUIState.value.copy(privacyPolicyAccepted = event.isChecked)
                printState()
            }
        }
        validateFields()
    }

    private fun register() {
        Log.d(TAG, "Registering...")
        printState()
        createUserInFirebase()
    }

    private fun validateFields() {
        val firstNameResult = Validator.validateFirstName(registerUIState.value.firstName)
        val lastNameResult = Validator.validateLastName(registerUIState.value.lastName)
        val emailResult = Validator.validateEmail(registerUIState.value.email)
        val passwordResult = Validator.validatePassword(registerUIState.value.password)
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(registerUIState.value.privacyPolicyAccepted)

        Log.d(TAG, "ValidateFields:")
        Log.d(TAG, "firstNameResult = $firstNameResult")
        Log.d(TAG, "lastNameResult = $lastNameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")
        Log.d(TAG, "privacyPolicyResult = $privacyPolicyResult")

        registerUIState.value = registerUIState.value.copy(
            firstNameError = ValidateResult(
                status = firstNameResult.status,
                message = firstNameResult.message
            ),
            lastNameError = ValidateResult(
                status = lastNameResult.status,
                message = lastNameResult.message
            ),
            emailError = ValidateResult(
                status = emailResult.status,
                message = emailResult.message
            ),
            passwordError = ValidateResult(
                status = passwordResult.status,
                message = passwordResult.message
            ),
            privacyPolicyError = ValidateResult(
                status = privacyPolicyResult.status,
                message = privacyPolicyResult.message
            )
        )

        allValidationsPassed.value = firstNameResult.status &&
            lastNameResult.status &&
            emailResult.status &&
            passwordResult.status &&
            privacyPolicyResult.status
    }

    private fun printState() {
        Log.d(TAG, "printState: ${registerUIState.value}")
    }

    private fun createUserInFirebase() {
        registerInProgress.value = true

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(
                registerUIState.value.email,
                registerUIState.value.password
            )
            .addOnCompleteListener {
                Log.d(TAG, "Firebase addOnCompleteListener: ${it.isSuccessful}")
                registerInProgress.value = false
                if (it.isSuccessful) {
                    AppRouter.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Firebase addOnFailureListener: ${it.localizedMessage}")
            }
    }
}
