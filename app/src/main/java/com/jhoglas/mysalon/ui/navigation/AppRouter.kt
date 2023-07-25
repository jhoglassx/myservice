package com.jhoglas.mysalon.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    object Register : Screen()
    object TermsAndConditions : Screen()
    object Login : Screen()
}

object AppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Register)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}
