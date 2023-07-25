package com.jhoglas.mysalon.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.compoment.ButtonComponent
import com.jhoglas.mysalon.ui.compoment.CheckboxComponent
import com.jhoglas.mysalon.ui.compoment.ClickableLoginTextComponent
import com.jhoglas.mysalon.ui.compoment.DividerComponent
import com.jhoglas.mysalon.ui.compoment.EmailFieldComponent
import com.jhoglas.mysalon.ui.compoment.HeadingTextComponent
import com.jhoglas.mysalon.ui.compoment.NormalTextComponent
import com.jhoglas.mysalon.ui.compoment.PasswordFieldComponent
import com.jhoglas.mysalon.ui.compoment.TextFieldComponent
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen

@Composable
fun RegisterScreen(loginViewModel: LoginViewModel = viewModel()) {
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
            NormalTextComponent(stringResource(id = R.string.hey_there))
            HeadingTextComponent(stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent(
                stringResource(id = R.string.first_name),
                onTextSelected = {
                    loginViewModel.onEvent(AuthUIEvent.FirstNameChange(it))
                },
                errorStatus = loginViewModel.authUIState.value.firstNameError
            )
            TextFieldComponent(
                stringResource(id = R.string.last_name),
                onTextSelected = {
                    loginViewModel.onEvent(AuthUIEvent.LastNameChange(it))
                },
                errorStatus = loginViewModel.authUIState.value.lastNameError
            )
            // Spacer(modifier = Modifier.height(20.dp))
            EmailFieldComponent(
                stringResource(id = R.string.email),
                onTextSelected = {
                    loginViewModel.onEvent(AuthUIEvent.EmailChange(it))
                },
                errorStatus = loginViewModel.authUIState.value.emailError
            )
            // EmailFieldComponent(stringResource(id = R.string.re_email))
            // Spacer(modifier = Modifier.height(20.dp))
            PasswordFieldComponent(
                stringResource(id = R.string.password),
                onTextSelected = {
                    loginViewModel.onEvent(AuthUIEvent.PasswordChange(it))
                },
                errorStatus = loginViewModel.authUIState.value.passwordError
            )
            // PasswordFieldComponent(stringResource(id = R.string.re_password))
            CheckboxComponent(
                onTextSelected = {
                    AppRouter.navigateTo(
                        Screen.TermsAndConditionsScreen
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                value = stringResource(id = R.string.register),
                onButtonClicker = {
                    loginViewModel.onEvent(AuthUIEvent.RegisterButtonClick)
                }
            )
            DividerComponent()
            ClickableLoginTextComponent(onTextSelected = {
                AppRouter.navigateTo(Screen.LoginScreen)
            })
        }
    }
}

@Preview
@Composable
fun RegisterPreview() {
    RegisterScreen()
}
