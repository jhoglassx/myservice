package com.jhoglas.mysalon.utils.extensions

fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+\$")
    return emailRegex.matches(this)
}

fun String.hasNumber(): Boolean {
    val emailRegex = Regex(".*\\d.*")
    return emailRegex.matches(this)
}

fun String.hasUppercase(): Boolean {
    val uppercaseRegex = Regex("[A-Z]")
    return uppercaseRegex.containsMatchIn(this)
}