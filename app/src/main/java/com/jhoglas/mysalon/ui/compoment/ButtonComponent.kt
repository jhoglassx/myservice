package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoglas.mysalon.ui.theme.Primary
import com.jhoglas.mysalon.ui.theme.PrimarySelected
import com.jhoglas.mysalon.ui.theme.Secondary

@Composable
fun ButtonComponent(
    value: String,
    onButtonClicker: () -> Unit,
    isEnable: Boolean = false,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        shape = shapesComponent.small,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnable,
        onClick = {
            onButtonClicker.invoke()
        }

    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
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
fun ScheduleButtonComponent(
    isEnable: Boolean = false,
    onButtonClicker: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(Primary, disabledContainerColor = PrimarySelected),
            enabled = isEnable,
            onClick = {
                onButtonClicker.invoke()
            }

        ) {
            Text(
                text = "SCHEDULE",
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal
                )
            )
        }
    }
}
