package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jhoglas.mysalon.domain.ProfessionalDomainEntity
import com.jhoglas.mysalon.ui.theme.Primary
import com.jhoglas.mysalon.ui.theme.PrimarySelected

@Composable
fun ProfessionalsComponent(
    professionals: List<ProfessionalDomainEntity>,
    professionalSelected: (ProfessionalDomainEntity) -> Unit,
) {
    CategoryTitleTextComponent(title = "Professionals")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        Alignment.Center
    ) {
        LazyRow(
            state = rememberLazyListState(),
        ) {
            itemsIndexed(professionals) { index, professional ->
                ProfessionalItem(professional, index, professionalSelected)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalItem(
    professional: ProfessionalDomainEntity,
    index: Int,
    onProfessionalSelected: (ProfessionalDomainEntity) -> Unit,
) {
    var color: Color = if (professional.isSelected) PrimarySelected else Primary

    if (index != 0) {
        Spacer(modifier = Modifier.size(8.dp))
    } else {
        Spacer(modifier = Modifier.size(0.dp))
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        onClick = {
            onProfessionalSelected(professional)
        }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(professional.photo)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp, 60.dp),
                alignment = Alignment.CenterEnd,
                contentScale = ContentScale.Crop
            )
            Text(
                text = professional.name,
                color = Color.White,
                fontWeight = SemiBold,
                modifier = Modifier
                    .padding(8.dp, 4.dp)
            )
        }
    }
}
