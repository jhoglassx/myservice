package com.jhoglas.mysalon.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jhoglas.mysalon.ui.auth.LoginScreen
import com.jhoglas.mysalon.ui.auth.RegisterScreen
import com.jhoglas.mysalon.ui.auth.TermsAndConditions
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.theme.MySalonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySalonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Crossfade(targetState = AppRouter.currentScreen) { currentState ->
                        when (currentState.value) {
                            is Screen.Register -> RegisterScreen()
                            is Screen.TermsAndConditions -> TermsAndConditions()
                            is Screen.Login -> LoginScreen()
                        }
                    }
                }
            }

//            val navController = rememberNavController()
//
//            MySalonTheme {
//                NavHost(navController = navController, startDestination = "register") {
//                    composable("login") {
//                        Login(navController)
//                    }
//                    composable("register") {
//                        Register(navController)
//                    }
//                    composable("home") {
//                        // A surface container using the 'background' color from the theme
//                        Surface(
//                            modifier = Modifier.fillMaxSize(),
//                            color = MaterialTheme.colorScheme.background,
//                        ) {
//                            Greeting("Android")
//                        }
//                    }
//                }
//            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MySalonTheme {
        Greeting("Android")
    }
}
