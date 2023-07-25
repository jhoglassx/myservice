package com.jhoglas.mysalon.ui.auth

sealed class AuthUIEvent() {
    data class FirstNameChange(val firstName: String) : AuthUIEvent()
    data class LastNameChange(val lastName: String) : AuthUIEvent()
    data class EmailChange(val email: String) : AuthUIEvent()
    data class PasswordChange(val password: String) : AuthUIEvent()

    object RegisterButtonClick : AuthUIEvent()
}
