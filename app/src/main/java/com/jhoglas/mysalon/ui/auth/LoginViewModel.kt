package com.jhoglas.mysalon.ui.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())
    val allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChange -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
                printState()
            }
            is LoginUIEvent.PasswordChange -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
                printState()
            }
            is LoginUIEvent.LoginButtonClick -> {
                login()
            }
        }
        validateFields()
    }

    private fun printState() {
        Log.d(TAG, "LoginUIState: $loginUIState")
    }

    private fun validateFields() {
        val emailResult = Validator.validateEmail(loginUIState.value.email)
        val passwordResult = Validator.validatePassword(loginUIState.value.password)

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    private fun login() {
        Log.d(TAG, "Logging in...")
        loginInProgress.value = true
        printState()
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(
                loginUIState.value.email,
                loginUIState.value.password
            )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "Login successful ${it.isSuccessful}")
                    loginInProgress.value = false
                    AppRouter.navigateTo(Screen.HomeScreen)
                } else {
                    loginInProgress.value = false
                    Log.d(TAG, "Login failed")
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Login failed: ${it.message}")
            }
    }

    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                AppRouter.navigateTo(Screen.LoginScreen)
                Log.d(TAG, "Firebase logout successful")
            } else {
                Log.d(TAG, "Firebase logout failed")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }
}
