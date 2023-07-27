package com.jhoglas.mysalon.ui.auth

object Validator {
    fun validateFirstName(firstName: String): ValidateResult {
        return ValidateResult(
            (firstName.isNullOrEmpty().not() && firstName.length >= 4)
        )
    }

    fun validateLastName(lastName: String): ValidateResult {
        return ValidateResult(
            (lastName.isNullOrEmpty().not() && lastName.length >= 4)
        )
    }

    fun validateEmail(email: String): ValidateResult {
        return ValidateResult(
            (email.isNullOrEmpty().not() && email.length >= 4)
        )
    }

    fun validatePassword(password: String): ValidateResult {
        return ValidateResult(
            (password.isNullOrEmpty().not() && password.length >= 5)
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidateResult {
        return ValidateResult(
            statusValue
        )
    }
}

data class ValidateResult(
    val status: Boolean = false
)
