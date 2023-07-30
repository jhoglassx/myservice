package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jhoglas.mysalon.ui.theme.Primary

@Composable
fun RatingWithReports(rating: Float, reportCount: Int, maxRating: Int = 5, starSize: Int = 18) {
    Row {
        RatingBar(rating, maxRating, starSize)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "($reportCount)",
            style = MaterialTheme.typography.bodySmall,
            color = Primary
        )
    }
}

@Composable
fun RatingBar(rating: Float, maxRating: Int = 5, starSize: Int = 18) {
    Row {
        repeat(maxRating) { index ->
            val icon = if (rating >= index + 1) {
                Icons.Default.Star
            } else {
                Icons.Default.StarOutline
            }
            StarIcon(icon, starSize)
        }
    }
}

@Composable
fun StarIcon(icon: ImageVector, starSize: Int) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Primary,
        modifier = Modifier.width(starSize.dp)
    )
}
