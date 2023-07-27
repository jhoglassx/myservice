package com.jhoglas.mysalon.ui.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhoglas.mysalon.ui.auth.LoginScreen
import com.jhoglas.mysalon.ui.auth.RegisterScreen
import com.jhoglas.mysalon.ui.auth.TermsAndConditionsScreen
import com.jhoglas.mysalon.ui.home.HomeScreen
import com.jhoglas.mysalon.ui.home.HomeViewModel
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.theme.MySalonTheme

@Composable
fun main(
    homeViewModel: HomeViewModel = viewModel(),
) {
    homeViewModel.checkForActiveSession()

    MySalonTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (homeViewModel.isUserLoggedIn.value == true) {
                AppRouter.navigateTo(Screen.HomeScreen)
            }

            Crossfade(targetState = AppRouter.currentScreen, label = "") { currentState ->
                when (currentState.value) {
                    is Screen.RegisterScreen -> RegisterScreen()
                    is Screen.TermsAndConditionsScreen -> TermsAndConditionsScreen()
                    is Screen.LoginScreen -> LoginScreen()
                    is Screen.HomeScreen -> HomeScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun mainPreview() {
    main()
}
