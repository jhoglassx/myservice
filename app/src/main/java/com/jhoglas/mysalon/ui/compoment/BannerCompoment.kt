package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jhoglas.mysalon.domain.EstablishmentDomainEntity

@Composable
fun BannerComponent(
    establishment: EstablishmentDomainEntity,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Transparent)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(establishment.img)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = "",
            alignment = Alignment.CenterStart,
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(Color.Transparent)
        ) {
            Text(
                style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 10f)),
                text = establishment.name,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
