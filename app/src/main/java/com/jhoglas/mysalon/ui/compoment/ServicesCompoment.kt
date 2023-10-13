package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoglas.mysalon.ui.theme.Primary

@Composable
fun ServicesComponent(
    services: List<String>,
) {
    CategoryTitleTextComponent("Services")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        Alignment.Center
    ) {
        LazyRow(
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(services) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary, shape = RoundedCornerShape(15)),
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it.uppercase(),
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp, 4.dp)
                    )
                }
            }
        }
    }
}
