package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jhoglas.mysalon.domain.entity.ProfessionalDomainEntity
import com.jhoglas.mysalon.ui.theme.Primary
import com.jhoglas.mysalon.ui.theme.PrimarySelected

@Composable
fun ProfessionalsComponent(
    professionals: List<ProfessionalDomainEntity>,
    professionalClicked: (ProfessionalDomainEntity) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            CategoryTitleTextComponent(title = "Professionals")
            Spacer(modifier = Modifier.height(8.dp))
            if (professionals.isEmpty()) {
                Text(
                    text = "Select a service for professional selection availability",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
            } else {
                LazyRow(
                    state = rememberLazyListState(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(professionals) { _, professional ->
                        ProfessionalItem(professional, professionalClicked)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalItem(
    professional: ProfessionalDomainEntity,
    onProfessionalSelected: (ProfessionalDomainEntity) -> Unit,
) {
    var color: Color = if (professional.isSelected) PrimarySelected else Primary
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        onClick = {
            onProfessionalSelected(professional)
        },
        shape = shapesComponent.small
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(professional.photo)
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .size(80.dp, 60.dp),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.Crop
        )
        Text(
            text = professional.name,
            color = Color.White,
            fontWeight = SemiBold,
            fontSize = 10.sp,
            modifier = Modifier
                .padding(8.dp, 4.dp)
        )
    }
}
