package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
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
fun EstablishmentListComponent() {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            EstablishmentItemComponent(
                imagePainter = painterResource(id = R.drawable.promotion),
                title = "Fruit",
                subtitle = "Start @",
                header = "$1",
            )
        }
        item {
            EstablishmentItemComponent(
                imagePainter = painterResource(id = R.drawable.promotion),
                title = "Meat",
                subtitle = "Discount",
                header = "20%",
            )
        }
        item {
            EstablishmentItemComponent(
                imagePainter = painterResource(id = R.drawable.promotion),
                title = "Meat",
                subtitle = "Discount",
                header = "20%",
            )
        }
        item {
            EstablishmentItemComponent(
                imagePainter = painterResource(id = R.drawable.promotion),
                title = "Meat",
                subtitle = "Discount",
                header = "20%",
            )
        }
        item {
            EstablishmentItemComponent(
                imagePainter = painterResource(id = R.drawable.promotion),
                title = "Meat",
                subtitle = "Discount",
                header = "20%",
            )
        }
    }
}

@Composable
fun EstablishmentItemComponent(
    title: String = "",
    subtitle: String = "",
    header: String = "",
    imagePainter: Painter,
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .background(Primary)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                alignment = Alignment.CenterEnd,
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.ExtraBold)
                    RatingBar(rating = 4.5f, starSize = 14)
                }
                Spacer(modifier = Modifier.height(4.dp))
                ServicesListComponent()
                Spacer(modifier = Modifier.height(8.dp))
                AddressWithMapIcon(address = "Rua Dublin 229, Duque de Caxias, Betim - MG")
            }
        }
    }
}

@Composable
fun ServicesListComponent() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            ServicesItemComponent(
                icon = Icons.Default.Home,
                title = "Corte"
            )
        }
        item {
            ServicesItemComponent(
                icon = Icons.Default.Home,
                title = "Corte"
            )
        }
        item {
            ServicesItemComponent(
                icon = Icons.Default.Home,
                title = "Corte"
            )
        }
    }
}

@Composable
fun ServicesItemComponent(
    title: String = "",
    icon: ImageVector,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        shape = RoundedCornerShape(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color.Black
            )
            Text(text = title, fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
