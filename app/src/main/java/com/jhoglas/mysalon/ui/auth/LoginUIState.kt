package com.jhoglas.mysalon.ui.auth

data class LoginUIState(

    var email: String = "",
    var password: String = "",
    var emailError: ValidateResult = ValidateResult(),
    var passwordError: ValidateResult = ValidateResult()

)
