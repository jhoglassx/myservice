package com.jhoglas.mysalon.ui.auth

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhoglas.mysalon.domain.entity.UserDomainEntity
import com.jhoglas.mysalon.domain.usecase.AuthClientUseCase
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authClientUseCase: AuthClientUseCase
) : ViewModel() {

    private val _allValidationsPassed = MutableStateFlow(false)
    val allValidationsPassed = _allValidationsPassed.asStateFlow()

    private val _loginState = MutableStateFlow(ScreenState())
    val loginState = _loginState.asStateFlow()

    private val _loginStateEmail = MutableStateFlow(ScreenState())
    val loginStateEmail = _loginStateEmail.asStateFlow()

    private val _loginStatePassword = MutableStateFlow(ScreenState())
    val loginStatePassword = _loginStatePassword.asStateFlow()

    private var _userDataState = MutableStateFlow(UserDomainEntity())
    var userDataState = _userDataState.asStateFlow()

    private var _isUserLoggedIn = MutableStateFlow(false)
    var isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    fun emailChange(value: String) {
        _loginStateEmail.value = loginStateEmail.value.copy(content = value)
        validateFields()
    }

    fun passwordChange(value: String) {
        _loginStatePassword.value = loginStatePassword.value.copy(content = value)
        validateFields()
    }

    fun loadingScreen(state: State) {
        _loginState.value = ScreenState(state = state)
    }

    private fun validateFields() {
        val emailResult = Validator.validateEmail(loginStateEmail.value.content.toString())
        val passwordResult = Validator.validatePassword(loginStatePassword.value.content.toString())

        Timber.tag(TAG).d("validateFields: Email $emailResult")
        Timber.tag(TAG).d("validateFields: Password $passwordResult")

        _loginStateEmail.value = loginStateEmail.value.copy(
            message = emailResult.message,
            state = if (emailResult.status) State.SUCCESS else State.ERROR,
        )

        _loginStatePassword.value = loginStatePassword.value.copy(
            message = passwordResult.message,
            state = if (passwordResult.status) State.SUCCESS else State.ERROR,
        )

        _allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    fun setUserData() {
        viewModelScope.launch {
            try {
                authClientUseCase.getSignedInUser().collect { user ->
                    _userDataState.value = user
                }
                Timber.tag(TAG).d("setUserData: Success ${_userDataState.value}")
            } catch (e: Exception) {
                e.printStackTrace()
                _loginState.value = ScreenState(
                    state = State.ERROR,
                    message = e.message ?: "Error"
                )
                Timber.tag(TAG).e("setUserData: Error $e")
            }
        }
    }

    fun checkForActiveSession() {
        viewModelScope.launch {
            try {
                authClientUseCase.checkForActiveSession().collect {
                    _isUserLoggedIn.value = it
                    Timber.tag(TAG).d("checkForActiveSession: Success ${_isUserLoggedIn.value}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.tag(TAG).e("checkForActiveSession Error: $e")
            }
        }
    }

    fun signInWithIntent(intent: Intent) {
        viewModelScope.launch {
            try {
                _loginState.value = authClientUseCase.signInWithIntent(intent)
                Timber.tag(TAG).d("signInWithIntent Success: ${_loginState.value}")
            } catch (e: Exception) {
                e.printStackTrace()
                _loginState.value = ScreenState(
                    state = State.ERROR,
                    message = e.message ?: "Error"
                )
                Timber.tag(TAG).e("signInWithIntent Error: $e")
            }
        }
    }

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = authClientUseCase.loginWithEmail(email, password)
                Timber.tag(TAG).d("loginWithEmail Success: ${_loginState.value}")
            } catch (e: Exception) {
                e.printStackTrace()
                _loginState.value = ScreenState(
                    state = State.ERROR,
                    message = e.message ?: "Error"
                )
                Timber.tag(TAG).e("loginWithEmail Error: $e")
            }
        }
    }

    fun loginWithGoogle(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) {
        viewModelScope.launch {
            try {
                val signInIntentSender = authClientUseCase.loginWithGoogle()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
                Timber.tag(TAG).d("loginWithGoogle Success:")
            } catch (e: Exception) {
                e.printStackTrace()
                _loginState.value = ScreenState(
                    state = State.ERROR,
                    message = e.message ?: "Error"
                )
                Timber.tag(TAG).e("loginWithGoogle Error: $e")
            }
        }
    }

    fun isLoggedUser(): Boolean = authClientUseCase.isLoggedUser()
    fun signOut() {
        viewModelScope.launch {
            try {
                authClientUseCase.signOut()
                Timber.tag(TAG).d("signOut Success:")
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.tag(TAG).e("signOut Error: $e")
            }
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}
