package com.jhoglas.mysalon.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    object RegisterScreen : Screen()
    object TermsAndConditionsScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
    object EstablishmentScreen : Screen()
}

object AppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)
    lateinit var bundle: Bundle

    fun navigateTo(destination: Screen, bundle: Bundle? = null) {
        if (bundle != null) {
            this.bundle = bundle
        }
        currentScreen.value = destination
    }
}
