package com.jhoglas.mysalon.ui.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    val allValidationsPassed = mutableStateOf(false)

    private val _registerState = MutableStateFlow(ScreenState())
    val registerState = _registerState.asStateFlow()
    val emailState = mutableStateOf(ScreenState())
    val passwordState = mutableStateOf(ScreenState())
    val nameState = mutableStateOf(ScreenState())
    private val policyState = mutableStateOf(ScreenState())

    fun nameChange(value: String) {
        nameState.value = nameState.value.copy(content = value)
        validateFields()
    }

    fun emailChange(value: String) {
        emailState.value = emailState.value.copy(content = value)
        validateFields()
    }

    fun passwordChange(value: String) {
        passwordState.value = passwordState.value.copy(content = value)
        validateFields()
    }

    fun privacyPolicyCheckChange(value: Boolean) {
        policyState.value = policyState.value.copy(content = value)
        validateFields()
    }

    fun loadingScreen(state: State) {
        _registerState.value = ScreenState(state = state)
    }

    private fun validateFields() {
        val firstNameResult = Validator.validateFirstName(nameState.value.content.toString())
        val emailResult = Validator.validateEmail(emailState.value.content.toString())
        val passwordResult = Validator.validatePassword(passwordState.value.content.toString())
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(policyState.value.content is Boolean)

        Log.d(TAG, "ValidateFields:")
        Log.d(TAG, "firstNameResult = $firstNameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")
        Log.d(TAG, "privacyPolicyResult = $privacyPolicyResult")

        nameState.value = nameState.value.copy(
            state = if (firstNameResult.status) State.SUCCESS else State.ERROR,
            message = firstNameResult.message
        )
        emailState.value = emailState.value.copy(
            state = if (emailResult.status) State.SUCCESS else State.ERROR,
            message = emailResult.message
        )
        passwordState.value = passwordState.value.copy(
            state = if (passwordResult.status) State.SUCCESS else State.ERROR,
            message = passwordResult.message
        )
        policyState.value = policyState.value.copy(
            state = if (privacyPolicyResult.status) State.SUCCESS else State.ERROR,
            message = privacyPolicyResult.message
        )

        allValidationsPassed.value = firstNameResult.status &&
            emailResult.status &&
            passwordResult.status &&
            privacyPolicyResult.status
    }

    companion object {
        const val TAG = "RegisterViewModel"
    }
}
