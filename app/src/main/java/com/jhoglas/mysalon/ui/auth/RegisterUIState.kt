package com.jhoglas.mysalon.ui.auth

data class RegisterUIState(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var privacyPolicyAccepted: Boolean = false,

    var firstNameError: ValidateResult = ValidateResult(),
    var lastNameError: ValidateResult = ValidateResult(),
    var emailError: ValidateResult = ValidateResult(),
    var passwordError: ValidateResult = ValidateResult(),
    var privacyPolicyError: ValidateResult = ValidateResult(),
)
