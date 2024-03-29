package com.jhoglas.mysalon.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhoglas.mysalon.domain.usecase.AuthClientUseCase
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authClientUseCase: AuthClientUseCase
) : ViewModel() {

    val allValidationsPassed = mutableStateOf(false)

    private val _registerState = MutableStateFlow(ScreenState())
    val registerState = _registerState.asStateFlow()
    val emailState = mutableStateOf(ScreenState())
    val passwordState = mutableStateOf(ScreenState())
    val nameState = mutableStateOf(ScreenState())
    val policyState = mutableStateOf(ScreenState())

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

        Timber.tag(TAG).d("ValidateFields:")
        Timber.tag(TAG).d("firstNameResult = $firstNameResult")
        Timber.tag(TAG).d("emailResult = $emailResult")
        Timber.tag(TAG).d("passwordResult = $passwordResult")
        Timber.tag(TAG).d("privacyPolicyResult = $privacyPolicyResult")

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

    fun registerUserInFirebase(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            loadingScreen(State.LOADING)
            try {
                authClientUseCase.registerUserInFirebase(name, email, password).collect {
                    val state = if (it) State.SUCCESS else State.ERROR
                    _registerState.value = ScreenState(
                        state = if (it) State.SUCCESS else State.ERROR,
                        message = state.toString()
                    )
                    Timber.tag(TAG).d("registerUserInFirebase Success = $_registerState")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e else null
                _registerState.value = ScreenState(
                    state = State.ERROR,
                    message = e.message ?: "Error"
                )
                Timber.tag(TAG).e("registerUserInFirebase Error = $e")
            }
        }
    }

    companion object {
        const val TAG = "RegisterViewModel"
    }
}
