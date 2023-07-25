package com.jhoglas.mysalon.ui.compoment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.theme.BgColor
import com.jhoglas.mysalon.ui.theme.GrayColor
import com.jhoglas.mysalon.ui.theme.Primary
import com.jhoglas.mysalon.ui.theme.Secondary
import com.jhoglas.mysalon.ui.theme.TextColor

@Composable
fun NormalTextComponent(
    value: String,
) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(
    value: String,
) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    labelValue: String,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean,
) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapesComponent.small),
        label = { Text(text = labelValue) },
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
        isError = !errorStatus,
    )
}

@Composable
fun UnderlineTextFieldComponent(
    value: String,
) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = GrayColor,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailFieldComponent(
    labelValue: String,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean,
) {
    val emailValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapesComponent.small),
        label = { Text(text = labelValue) },
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
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldComponent(
    labelValue: String,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean,
) {
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapesComponent.small),
        label = { Text(text = labelValue) },
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
        isError = !errorStatus
    )
}

@Composable
fun CheckboxComponent(
    onTextSelected: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState = remember { mutableStateOf(false) }
        Checkbox(
            modifier = Modifier
                .scale(0.8f)
                .clip(shapesComponent.small),
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
            }
        )
        ClickableTextComponent(onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(
    onTextSelected: (String) -> Unit,
) {
    val initialText = "By continuing your accept our "
    val privacyPolicyText = "Privacy Policy "
    val andText = "and "
    val termOfUseText = "Term of Use"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = termOfUseText, annotation = termOfUseText)
            append(termOfUseText)
        }
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontSize = 12.sp
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                Log.d("ClickableTextCompoment", "${span.item}")

                if ((span.item == termOfUseText) || (span.item == privacyPolicyText)) {
                    onTextSelected(span.item)
                }
            }
        }
    )
}

@Composable
fun ClickableLoginTextComponent(
    tryingToLogin: Boolean = true,
    onTextSelected: (String) -> Unit,
) {
    val initialText = if (tryingToLogin)"Already have an account? " else "Don't have an account yet? "
    val loginText = if (tryingToLogin) "Login " else "Sign up"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                Log.d("ClickableLoginTextComponent", "${span.item}")
                if (span.item == loginText) {
                    onTextSelected(span.item)
                }
            }
        }
    )
}

@Composable
fun ButtonComponent(
    value: String,
    onButtonClicker: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        shape = shapesComponent.small,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = {
            onButtonClicker.invoke()
        }

    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(48.dp).background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
        }
    }
}

@Composable
fun DividerComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = GrayColor,
            thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.or),
            fontSize = 14.sp,
            color = TextColor
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = GrayColor,
            thickness = 1.dp
        )
    }
}
