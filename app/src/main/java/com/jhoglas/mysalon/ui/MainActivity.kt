package com.jhoglas.mysalon.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.jhoglas.mysalon.ui.auth.GoogleAuthUiClient
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
            main(applicationContext)
        }
    }
}

@Composable
fun main(
    context: Context,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    loginViewModel.checkForActiveSession()
    Text(text = "estou aqui")
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
                    is Screen.LoginScreen -> LoginScreen(googleAuthUiClient)
                    is Screen.HomeScreen -> HomeScreen(googleAuthUiClient)
                    is Screen.EstablishmentScreen -> EstablishmentScreen(googleAuthUiClient)
                }
            }
        }
    }
}
