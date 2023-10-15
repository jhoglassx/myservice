package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.theme.BgColor
import com.jhoglas.mysalon.ui.theme.ErrorColor
import com.jhoglas.mysalon.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    labelValue: String,
    onTextSelected: (String) -> Unit,
    screenState: ScreenState,
) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapesComponent.small),
        label = { Text(text = labelValue) },
        supportingText = {
            if (screenState.state == State.ERROR) Text(text = screenState.message, color = ErrorColor)
        },
        value = textValue.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.profile), contentDescription = "")
        },
        isError = screenState.state == State.ERROR
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailFieldComponent(
    labelValue: String,
    onTextSelected: (String) -> Unit,
    screenState: ScreenState,
) {
    val emailValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapesComponent.small),
        label = { Text(text = labelValue) },
        supportingText = {
            if (screenState.state == State.ERROR) Text(text = screenState.message, color = ErrorColor)
        },
        value = emailValue.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            emailValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.email), contentDescription = "")
        },
        isError = screenState.state == State.ERROR
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldComponent(
    labelValue: String,
    onTextSelected: (String) -> Unit,
    screenState: ScreenState,
) {
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapesComponent.small),
            label = { Text(text = labelValue) },
            supportingText = {
                if (screenState.state == State.ERROR) Text(text = screenState.message, color = ErrorColor)
            },
            value = password.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Primary,
                focusedLabelColor = Primary,
                cursorColor = Primary,
                containerColor = BgColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            onValueChange = {
                password.value = it
                onTextSelected(it)
            },
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.lock), contentDescription = "")
            },
            trailingIcon = {
                val iconImage = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                var description = if (passwordVisible.value) stringResource(id = R.string.hide_password) else stringResource(id = R.string.show_password)

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = iconImage, contentDescription = description)
                }
            },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            isError = screenState.state == State.ERROR
        )
    }
}
