package com.jhoglas.mysalon.ui.auth

import com.jhoglas.mysalon.utils.extensions.hasNumber
import com.jhoglas.mysalon.utils.extensions.hasUppercase
import com.jhoglas.mysalon.utils.extensions.isValidEmail

object Validator {
    fun validateFirstName(firstName: String) = when {
        firstName.isNullOrEmpty() -> ValidateResult(
            status = false,
            message = "First name is not empty",
        )

        firstName.length < 1 -> ValidateResult(
            status = false,
            message = "First name must contain 1 or more digits",
        )

        else -> ValidateResult(status = true, message = "First name is valid")
    }

    fun validateLastName(lastName: String) = when {
        lastName.isNullOrEmpty() -> ValidateResult(
            status = false,
            message = "Last name is not empty",
        )

        lastName.length < 1 -> ValidateResult(
            status = false,
            message = "Last name must contain 1 or more digits",
        )

        else -> ValidateResult(status = true, message = "Last name is valid")
    }

    fun validateEmail(email: String) = when {
        email.isNullOrEmpty() -> ValidateResult(
            status = false,
            message = "Email is not empty",
        )

        email.isValidEmail().not() -> ValidateResult(
            status = false,
            message = "Invalid email",
        )

        else -> ValidateResult(status = true, message = "Email is valid")
    }

    fun validatePassword(password: String) = when {
        password.isNullOrEmpty() -> ValidateResult(
            status = false,
            message = "Password is not empty",
        )

        password.length < 6 -> ValidateResult(
            status = false,
            message = "Password must contain 6 or more digits",
        )

        password.hasNumber().not() -> ValidateResult(
            status = false,
            message = "Password must contain one or more numbers",
        )

        password.hasUppercase().not() -> ValidateResult(
            status = false,
            message = "Password must contain one or more uppercase digits",
        )

        else -> ValidateResult(status = true, message = "Password is valid")
    }

    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidateResult {
        return ValidateResult(
            status = statusValue,
            message = "You must accept the privacy policy",
        )
    }
}

data class ValidateResult(
    val status: Boolean = true,
    val message: String = "",
)
