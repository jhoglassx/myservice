package com.jhoglas.mysalon.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.compoment.HeadingTextComponent
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.navigation.SystemBackButtonHandler

@Composable
fun TermsAndConditions() {
    Surface(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))
        SystemBackButtonHandler {
            AppRouter.navigateTo(Screen.Register)
        }
    }
}

@Preview
@Composable
fun TermsAndConditionsPreview() {
    TermsAndConditions()
}
