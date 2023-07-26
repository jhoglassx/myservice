package com.jhoglas.mysalon.ui.auth

sealed class RegisterUIEvent() {
    data class FirstNameChange(val firstName: String) : RegisterUIEvent()
    data class LastNameChange(val lastName: String) : RegisterUIEvent()
    data class EmailChange(val email: String) : RegisterUIEvent()
    data class PasswordChange(val password: String) : RegisterUIEvent()
    data class PrivacyPolicyCheckChange(val isChecked: Boolean) : RegisterUIEvent()

    object RegisterButtonClick : RegisterUIEvent()
}
