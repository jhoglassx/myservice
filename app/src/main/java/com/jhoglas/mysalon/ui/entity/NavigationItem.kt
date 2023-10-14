package com.jhoglas.mysalon.ui.entity

import androidx.compose.ui.graphics.vector.ImageVector
import com.jhoglas.mysalon.ui.navigation.Screen

data class NavigationItem(
    val title: String,
    val description: String,
    val itemId: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: Screen
)
