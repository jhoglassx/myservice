package com.jhoglas.mysalon.ui.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val auth = Firebase.auth
    private val database = Firebase.database

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

    fun onSignInResult(screenState: ScreenState) {
        if (screenState.content != null && screenState.state == State.SUCCESS) {
            Log.d(TAG, "Login successful")
            _loginState.value = screenState
            AppRouter.navigateTo(Screen.HomeScreen)
        } else {
            Log.d(TAG, "Login failed: ${screenState.message}")
        }
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

    fun checkForActiveSession() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            Log.d(TAG, "Valid session")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "Invalid session")
            isUserLoggedIn.value = false
        }
    }

    fun getSignedInUser() {
        viewModelScope.launch {
            try {
                val data = database.getReference("accounts")
                    .child(auth.currentUser?.uid ?: "")
                    .get()
                    .await()

                userDataState.value = UserData(
                    name = data.child("name")?.value.toString(),
                    email = data.child("email")?.value.toString(),
                    phoneNumber = data.child("phoneNumber")?.value.toString(),
                    image = data.child("image")?.value.toString(),
                    dateCreate = data.child("dateCreate")?.value.toString(),
                    dateUpdate = data.child("dateUpdate")?.value.toString(),
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadingScreen() {
        _loginState.update {
            it.copy(state = State.LOADING)
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}
