package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryListCardComponent(
    title: String = "",
    icon: ImageVector,
) {
    Card(
        modifier = Modifier
            .height(18.dp),
        shape = RoundedCornerShape(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF7283BB))
                .padding(6.dp, 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color.LightGray,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = title.uppercase(), fontSize = 10.sp, color = Color.LightGray, fontWeight = FontWeight.Bold)
        }
    }
}
