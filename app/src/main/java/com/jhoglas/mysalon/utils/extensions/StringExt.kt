package com.jhoglas.mysalon.utils.extensions

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color

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

fun String.hexToRgb(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

