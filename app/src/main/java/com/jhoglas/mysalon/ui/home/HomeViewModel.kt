package com.jhoglas.mysalon.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhoglas.mysalon.domain.getEstablishments
import com.jhoglas.mysalon.ui.entity.NavigationItem
import com.jhoglas.mysalon.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            description = "Home Screen",
            itemId = "homeScreen",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            screen = Screen.HomeScreen
        ),
        NavigationItem(
            title = "Settings",
            description = "Settings Screen",
            itemId = "settingsScreen",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            screen = Screen.HomeScreen
        ),
        NavigationItem(
            title = "Favorite",
            description = "Favorite Screen",
            itemId = "favoriteScreen",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            screen = Screen.HomeScreen
        )
    )

    private val emailId: MutableLiveData<String> = MutableLiveData()

    fun listEstablishment() = getEstablishments()

    companion object {
        const val TAG = "HomeViewModel"
    }
}
