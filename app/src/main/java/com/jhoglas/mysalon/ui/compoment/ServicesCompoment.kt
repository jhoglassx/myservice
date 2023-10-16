package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoglas.mysalon.domain.entity.ServiceDomainEntity
import com.jhoglas.mysalon.ui.theme.Primary
import com.jhoglas.mysalon.ui.theme.PrimarySelected

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesComponent(
    services: List<ServiceDomainEntity>,
    onServiceClicked: (ServiceDomainEntity) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Column {
            CategoryTitleTextComponent("Services")
            LazyRow(
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(services) { service ->
                    var color: Color = if (service.isSelected) PrimarySelected else Primary
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = color
                        ),
                        onClick = {
                            onServiceClicked.invoke(service)
                        },
                        shape = shapesComponent.small
                    ) {
                        Text(
                            text = service.title.uppercase(),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp, 4.dp)
                        )
                    }
                }
            }
        }


    }
}
