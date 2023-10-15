package com.jhoglas.mysalon.ui.auth

data class SignInState(
    val isSignInSuccessfull: Boolean = false,
    val signInError: String? = null,
)
