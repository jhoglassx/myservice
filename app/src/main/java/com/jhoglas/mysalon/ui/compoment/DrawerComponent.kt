package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.entity.NavigationItem
import com.jhoglas.mysalon.ui.theme.Primary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navigationItem: List<NavigationItem>
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(200.dp),
    ) {
        NavigationDrawerHeader()
        NavigationDrawerBody(navigationItem = navigationItem)
    }
}

@Composable
fun NavigationDrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary)
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.navigation_header))
    }
}

@Composable
fun NavigationDrawerBody(
    navigationItem: List<NavigationItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ){
        items(navigationItem){
            NavigationItemRow(item = it)
        }
    }
}

@Composable
fun NavigationItemRow(item : NavigationItem){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.description,
            tint = Primary
        )
        Spacer(modifier = Modifier.width(18.dp))
        NormalTextComponent(value = item.title)
    }
}




