package com.jhoglas.mysalon.ui.auth

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhoglas.mysalon.domain.usecase.AuthClientUseCase
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.entity.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authClientUseCase: AuthClientUseCase
) : ViewModel() {

    val allValidationsPassed = mutableStateOf(false)

    private val _loginState = MutableStateFlow(ScreenState())
    val loginState = _loginState.asStateFlow()
    val loginStateEmail = mutableStateOf(ScreenState())
    val loginStatePassword = mutableStateOf(ScreenState())
    var userDataState = mutableStateOf(UserData())
        private set

    fun emailChange(value: String) {
        loginStateEmail.value = loginStateEmail.value.copy(content = value)
        validateFields()
    }

    fun passwordChange(value: String) {
        loginStatePassword.value = loginStatePassword.value.copy(content = value)
        validateFields()
    }

    fun loadingScreen(state: State) {
        _loginState.value = ScreenState(state = state)
    }

    private fun validateFields() {
        val emailResult = Validator.validateEmail(loginStateEmail.value.content.toString())
        val passwordResult = Validator.validatePassword(loginStatePassword.value.content.toString())

        loginStateEmail.value = loginStateEmail.value.copy(
            message = emailResult.message,
            state = if (emailResult.status) State.SUCCESS else State.ERROR,
        )

        loginStatePassword.value = loginStatePassword.value.copy(
            message = passwordResult.message,
            state = if (passwordResult.status) State.SUCCESS else State.ERROR,
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun setUserData() {
        viewModelScope.launch {
            userDataState.value = authClientUseCase.getSignedInUser().content as UserData
        }
    }

    fun checkForActiveSession() {
        authClientUseCase.checkForActiveSession()
    }

    fun signInWithIntent(intent: Intent) {
        viewModelScope.launch {
            _loginState.value = authClientUseCase.signInWithIntent(intent)
        }
    }

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = authClientUseCase.loginWithEmail(email, password)
        }
    }

    fun loginWithGoogle(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) {
        viewModelScope.launch {
            val signInIntentSender = authClientUseCase.loginWithGoogle()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
    }

    fun isLoggedUser(): Boolean = authClientUseCase.isLoggedUser()
    fun signOut() {
        viewModelScope.launch {
            authClientUseCase.signOut()
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}
