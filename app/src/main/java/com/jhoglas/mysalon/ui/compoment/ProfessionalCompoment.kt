package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.theme.Primary

@Preview
@Composable
fun ProfessionalListComponent() {
    CategoryTitleTextComponent()
    LazyColumn(
        Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfessionalItem(
                imagePainter = painterResource(id = R.drawable.cabelereiro),
                title = "Fruit"
            )
        }
        item {
            ProfessionalItem(
                imagePainter = painterResource(id = R.drawable.cabelereiro),
                title = "Meat"
            )
        }
        item {
            ProfessionalItem(
                imagePainter = painterResource(id = R.drawable.cabelereiro),
                title = "Meat"
            )
        }
    }
}

@Composable
fun ProfessionalItem(
    title: String = "",
    imagePainter: Painter,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "",
                modifier = Modifier
                    .size(106.dp),
                alignment = Alignment.CenterEnd,
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.ExtraBold)
                    RatingBar(rating = 4.5f, starSize = 14)
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(4.dp))
                ServicesListComponent()
                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                ScheduleButtonComponent(
                    onButtonClicker = {
                    }
                )
            }
        }
    }
}
