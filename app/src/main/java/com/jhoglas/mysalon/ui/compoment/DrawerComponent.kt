package com.jhoglas.mysalon.ui.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.entity.NavigationItem
import com.jhoglas.mysalon.ui.theme.Primary
import com.jhoglas.mysalon.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    value: String?,
    navigationItem: List<NavigationItem>,
    onNavigationItemClicked: (NavigationItem) -> Unit,
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(200.dp),
        drawerShape = RoundedCornerShape(0.dp)
    ) {
        NavigationDrawerHeader(value)
        NavigationDrawerBody(
            navigationItem = navigationItem,
            onNavigationItemClicked = onNavigationItemClicked
        )
    }
}

@Composable
fun NavigationDrawerHeader(
    value: String?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary)
    ) {
        HeadingTextComponent(value ?: stringResource(id = R.string.navigation_header))
    }
}

@Composable
fun NavigationDrawerBody(
    navigationItem: List<NavigationItem>,
    onNavigationItemClicked: (NavigationItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(navigationItem) {
            NavigationItemRow(
                item = it,
                onNavigationItemClicked = onNavigationItemClicked
            )
        }
    }
}

@Composable
fun NavigationItemRow(
    item: NavigationItem,
    onNavigationItemClicked: (NavigationItem) -> Unit
) {
    val shadowOffset = Offset(4f, 6f)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigationItemClicked.invoke(item)
            }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = item.selectedIcon,
            contentDescription = item.description,
            tint = Primary
        )
        Spacer(modifier = Modifier.width(18.dp))
        Text(
            text = item.title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                shadow = Shadow(
                    color = Secondary,
                    offset = shadowOffset,
                    2f
                )
            )
        )
    }
}
