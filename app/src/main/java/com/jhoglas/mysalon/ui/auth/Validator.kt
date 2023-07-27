package com.jhoglas.mysalon.ui.auth

import com.jhoglas.mysalon.utils.extensions.hasNumber
import com.jhoglas.mysalon.utils.extensions.hasUppercase
import com.jhoglas.mysalon.utils.extensions.isValidEmail

object Validator {
    fun validateFirstName(firstName: String): ValidateResult {
        val rule = (firstName.isNullOrEmpty().not() && firstName.length >= 4)

        return ValidateResult(
            status = rule,
            message = "Invalid field"
        )
    }

    fun validateLastName(lastName: String): ValidateResult {
        val rule = (lastName.isNullOrEmpty().not() && lastName.length >= 4)

        return ValidateResult(
            status = rule,
            message = "Invalid field"
        )
    }

    fun validateEmail(email: String): ValidateResult {
        val rule = (email.isNullOrEmpty().not() && email.isValidEmail() && email.length >= 6)

        return ValidateResult(
            status = rule,
            message = "Invalid email"
        )
    }

    fun validatePassword(password: String): ValidateResult {
        var isValid = ValidateResult()

        if (password.isNullOrEmpty()) {
            isValid = ValidateResult(
                status = false,
                message = "Password is not empty"
            )
        }

        if (password.length < 6) {
            isValid = ValidateResult(
                status = false,
                message = "Password must contain 6 or more digits"
            )
        }

        if (password.hasNumber().not()) {
            isValid = ValidateResult(
                status = false,
                message = "Password must contain one or more numbers"
            )
        }

        if (password.hasUppercase().not()) {
            isValid = ValidateResult(
                status = false,
                message = "Password must contain one or more uppercase digits"
            )
        }

        return isValid
    }

    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidateResult {
        return ValidateResult(
            status = statusValue,
            message = "You must accept the privacy policy"
        )
    }
}

data class ValidateResult(
    val status: Boolean = true,
    val message: String = "",
)
