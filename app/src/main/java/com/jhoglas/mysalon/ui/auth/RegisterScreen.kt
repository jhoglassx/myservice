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
import com.jhoglas.mysalon.ui.compoment.CheckboxComponent
import com.jhoglas.mysalon.ui.compoment.ClickableLoginTextComponent
import com.jhoglas.mysalon.ui.compoment.DividerComponent
import com.jhoglas.mysalon.ui.compoment.EmailFieldComponent
import com.jhoglas.mysalon.ui.compoment.HeadingTextComponent
import com.jhoglas.mysalon.ui.compoment.NormalTextComponent
import com.jhoglas.mysalon.ui.compoment.PasswordFieldComponent
import com.jhoglas.mysalon.ui.compoment.TextFieldComponent
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.navigation.SystemBackButtonHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val registerState by registerViewModel.registerState.collectAsState()
    val emailState = registerViewModel.emailState.value
    val passwordState = registerViewModel.passwordState.value
    val nameState = registerViewModel.nameState.value

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
                NormalTextComponent(stringResource(id = R.string.hey_there))
                HeadingTextComponent(stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                TextFieldComponent(
                    stringResource(id = R.string.first_name),
                    onTextSelected = {
                        registerViewModel.nameChange(it)
                    },
                    screenState = nameState
                )
                EmailFieldComponent(
                    stringResource(id = R.string.email),
                    onTextSelected = {
                        registerViewModel.emailChange(it)
                    },
                    screenState = emailState
                )
                PasswordFieldComponent(
                    stringResource(id = R.string.password),
                    onTextSelected = {
                        registerViewModel.passwordChange(it)
                    },
                    screenState = passwordState
                )
                CheckboxComponent(
                    onTextSelected = {
                        AppRouter.navigateTo(
                            Screen.TermsAndConditionsScreen
                        )
                    },
                    onCheckedChange = {
                        registerViewModel.privacyPolicyCheckChange(it)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicker = {
                        GlobalScope.launch {
                            registerViewModel.loadingScreen(State.LOADING)
                            registerViewModel.registerUserInFirebase(
                                name = nameState.content.toString(),
                                email = emailState.content.toString(),
                                password = passwordState.content.toString(),
                            )
                            registerViewModel.loadingScreen(registerState.state)
                        }
                    },
                    isEnable = registerViewModel.allValidationsPassed.value
                )
                DividerComponent()
                ClickableLoginTextComponent(onTextSelected = {
                    AppRouter.navigateTo(Screen.LoginScreen)
                })
            }
        }
        if (registerState.state == State.SUCCESS) {
            AppRouter.navigateTo(Screen.HomeScreen)
        }
        if (registerState.state == State.LOADING) {
            CircularProgressIndicator()
        }
    }

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.LoginScreen)
    }
}
