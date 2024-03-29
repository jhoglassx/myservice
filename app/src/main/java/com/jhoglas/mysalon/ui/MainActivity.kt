package com.jhoglas.mysalon.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jhoglas.mysalon.ui.auth.LoginScreen
import com.jhoglas.mysalon.ui.auth.LoginViewModel
import com.jhoglas.mysalon.ui.auth.RegisterScreen
import com.jhoglas.mysalon.ui.auth.TermsAndConditionsScreen
import com.jhoglas.mysalon.ui.establishment.EstablishmentScreen
import com.jhoglas.mysalon.ui.home.HomeScreen
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.theme.MySalonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            main()
        }
    }
}

@Composable
fun main(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    loginViewModel.checkForActiveSession()
    MySalonTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            if (loginViewModel.isUserLoggedIn.value == true) {
                AppRouter.navigateTo(
                    Screen.EstablishmentScreen,
                    Bundle().apply {
                        putString("establishmentId", "1")
                    }
                )
            }

            Crossfade(targetState = AppRouter.currentScreen, label = "") { currentState ->
                when (currentState.value) {
                    is Screen.RegisterScreen -> RegisterScreen()
                    is Screen.TermsAndConditionsScreen -> TermsAndConditionsScreen()
                    is Screen.LoginScreen -> LoginScreen()
                    is Screen.HomeScreen -> HomeScreen()
                    is Screen.EstablishmentScreen -> EstablishmentScreen()
                }
            }
        }
    }
}
