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
import com.jhoglas.mysalon.ui.home.HomeViewModel
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val auth = Firebase.auth
    private val database = Firebase.database

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())
    val allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)
    var userDataState = mutableStateOf(UserData())
        private set

    fun onEvent(event: LoginUIEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginUIEvent.EmailChange -> {
                    loginUIState.value = loginUIState.value.copy(email = event.email)
                }

                is LoginUIEvent.PasswordChange -> {
                    loginUIState.value = loginUIState.value.copy(password = event.password) }

                is LoginUIEvent.LoginButtonClick -> {
                    loginWithEmail()
                }
            }
            validateFields()
        }
    }

    fun onSignInResult(signInResult: SignInResult) {
        if (signInResult.data != null) {
            Log.d(TAG, "Login successful")
            AppRouter.navigateTo(Screen.HomeScreen)
        } else {
            Log.d(TAG, "Login failed: ${signInResult.errorMessage}")
        }
    }

    private fun validateFields() {
        val emailResult = Validator.validateEmail(loginUIState.value.email)
        val passwordResult = Validator.validatePassword(loginUIState.value.password)

        loginUIState.value = loginUIState.value.copy(
            emailError = ValidateResult(
                status = emailResult.status,
                message = emailResult.message
            ),
            passwordError = ValidateResult(
                status = passwordResult.status,
                message = passwordResult.message
            )
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    private fun loginWithEmail() {
        Log.d(TAG, "Logging in...")
        loginInProgress.value = true
        auth.signInWithEmailAndPassword(
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
    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun checkForActiveSession() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            Log.d(HomeViewModel.TAG, "Valid session")
            isUserLoggedIn.value = true
        } else {
            Log.d(HomeViewModel.TAG, "Invalid session")
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
}
