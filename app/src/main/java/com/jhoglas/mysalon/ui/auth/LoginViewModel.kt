package com.jhoglas.mysalon.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.entity.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

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

    fun setUserData(screenState: ScreenState) {
        userDataState.value = screenState.content as UserData
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}
