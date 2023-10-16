package com.jhoglas.mysalon.ui.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.compoment.ButtonComponent
import com.jhoglas.mysalon.ui.compoment.ButtonComponentLoginWithGoogle
import com.jhoglas.mysalon.ui.compoment.ClickableLoginTextComponent
import com.jhoglas.mysalon.ui.compoment.DividerComponent
import com.jhoglas.mysalon.ui.compoment.EmailFieldComponent
import com.jhoglas.mysalon.ui.compoment.HeadingTextComponent
import com.jhoglas.mysalon.ui.compoment.NormalTextComponent
import com.jhoglas.mysalon.ui.compoment.PasswordFieldComponent
import com.jhoglas.mysalon.ui.compoment.UnderlineTextComponent
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.navigation.SystemBackButtonHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    auth: AuthClient,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val loginState by loginViewModel.loginState.collectAsState()
    val loginStateEmail = loginViewModel.loginStateEmail.value
    val loginStatePassword = loginViewModel.loginStatePassword.value

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                GlobalScope.launch {
                    loginViewModel.loadingScreen(State.LOADING)
                    val signInResult = auth.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    loginViewModel.loadingScreen(signInResult.state)
                }
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NormalTextComponent(stringResource(id = R.string.login))
                HeadingTextComponent(stringResource(id = R.string.welcome_back))
                Spacer(modifier = Modifier.height(20.dp))

                EmailFieldComponent(
                    stringResource(id = R.string.email),
                    onTextSelected = {
                        loginViewModel.emailChange(it)
                    },
                    screenState = loginStateEmail
                )
                PasswordFieldComponent(
                    stringResource(id = R.string.password),
                    onTextSelected = {
                        loginViewModel.passwordChange(it)
                    },
                    screenState = loginStatePassword
                )
                Spacer(modifier = Modifier.height(20.dp))
                UnderlineTextComponent(value = stringResource(id = R.string.forgot_password))

                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicker = {
                        GlobalScope.launch {
                            loginViewModel.loadingScreen(State.LOADING)
                            val signInResult = auth.loginWithEmail(
                                email = loginStateEmail.content.toString(),
                                password = loginStatePassword.content.toString()
                            )
                            loginViewModel.loadingScreen(signInResult.state)
                        }
                    },
                    isEnable = loginViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(10.dp))
                DividerComponent()
                Spacer(modifier = Modifier.height(5.dp))
                ButtonComponentLoginWithGoogle(
                    value = stringResource(id = R.string.login_with_google),
                    onButtonClicker = {
                        GlobalScope.launch {
                            val signInIntentSender = auth.loginWithGoogle()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    isEnable = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                ClickableLoginTextComponent(
                    tryingToLogin = false,
                    onTextSelected = {
                        AppRouter.navigateTo(Screen.RegisterScreen)
                    }
                )
            }
        }
        if (auth.isLoggedUser() && loginState.state == State.SUCCESS) {
            AppRouter.navigateTo(Screen.HomeScreen)
        }

        if (loginState.state == State.LOADING) {
            CircularProgressIndicator()
        }
    }

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.RegisterScreen)
    }
}
