package com.jhoglas.mysalon.ui.compoment

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jhoglas.mysalon.domain.entity.EstablishmentDomainEntity
import com.jhoglas.mysalon.domain.entity.ServiceDomainEntity
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import com.jhoglas.mysalon.ui.theme.Primary

@Composable
fun EstablishmentListComponent(
    listEstablishment: List<EstablishmentDomainEntity>,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = listEstablishment
        ) { _, item ->
            EstablishmentItemComponent(item = item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstablishmentItemComponent(
    item: EstablishmentDomainEntity,
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(110.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            AppRouter.navigateTo(
                Screen.EstablishmentScreen,
                Bundle().apply {
                    putString("establishmentId", item.id)
                }
            )
        }
    ) {
        Row(
            modifier = Modifier
                .background(Primary)
                .padding(2.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.img)
                    .crossfade(true)
                    .build(),
                contentDescription = item.description,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(8.dp)),
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
                    Text(text = item.name, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.ExtraBold)
                    RatingBar(rating = item.rating, starSize = 14)
                }
                Spacer(modifier = Modifier.height(4.dp))
                ServicesListComponent(
                    item.services
                )
                Spacer(modifier = Modifier.height(8.dp))
                AddressWithMapIcon(address = item.address)
            }
        }
    }
}

@Composable
fun ServicesListComponent(
    sevices: List<ServiceDomainEntity>,
) {
    Column(
        // verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Services Disponiveis:", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(sevices) { item ->
                ServicesItemComponent(item)
            }
        }
    }
}

@Composable
fun ServicesItemComponent(item: ServiceDomainEntity) {
    CategoryListCardComponent(title = item.title, icon = Icons.Default.Home)
}
