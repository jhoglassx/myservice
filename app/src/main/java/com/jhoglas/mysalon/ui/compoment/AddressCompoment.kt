package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddressWithMapIcon(address: String) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        MapIcon()
        Spacer(modifier = Modifier.width(8.dp))
        AddressText(address)
    }
}

@Composable
fun MapIcon() {
    Icon(
        imageVector = Icons.Filled.LocationOn,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.padding(end = 4.dp)
    )
}

@Composable
fun AddressText(address: String) {
    Text(
        text = address,
        style = MaterialTheme.typography.bodySmall,
        color = Color.White
    )
}
