package com.jhoglas.mysalon.ui.auth

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.compoment.ButtonComponent
import com.jhoglas.mysalon.ui.compoment.ClickableLoginTextComponent
import com.jhoglas.mysalon.ui.compoment.DividerComponent
import com.jhoglas.mysalon.ui.compoment.EmailFieldComponent
import com.jhoglas.mysalon.ui.compoment.HeadingTextComponent
import com.jhoglas.mysalon.ui.compoment.NormalTextComponent
import com.jhoglas.mysalon.ui.compoment.PasswordFieldComponent
import com.jhoglas.mysalon.ui.compoment.UnderlineTextComponent
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.navigation.SystemBackButtonHandler

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
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
                        loginViewModel.onEvent(LoginUIEvent.EmailChange(it))
                    },
                    error = loginViewModel.loginUIState.value.emailError
                )
                PasswordFieldComponent(
                    stringResource(id = R.string.password),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChange(it))
                    },
                    error = loginViewModel.loginUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(20.dp))
                UnderlineTextComponent(value = stringResource(id = R.string.forgot_password))

                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicker = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClick)
                    },
                    isEnable = loginViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicker = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClick)
                    },
                    isEnable = loginViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))
                DividerComponent()
                ClickableLoginTextComponent(
                    tryingToLogin = false,
                    onTextSelected = {
                        AppRouter.navigateTo(Screen.RegisterScreen)
                    }
                )
            }
        }

        if (loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.RegisterScreen)
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
