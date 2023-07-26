package com.jhoglas.mysalon.ui.auth

sealed class LoginUIEvent() {

    data class EmailChange(val email: String) : LoginUIEvent()
    data class PasswordChange(val password: String) : LoginUIEvent()

    object LoginButtonClick : LoginUIEvent()
}
