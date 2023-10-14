package com.jhoglas.mysalon.ui.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.jhoglas.mysalon.domain.getEstablishments
import com.jhoglas.mysalon.ui.entity.NavigationItem
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen

class HomeViewModel() : ViewModel() {

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
    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    private val emailId: MutableLiveData<String> = MutableLiveData()

    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                AppRouter.navigateTo(Screen.LoginScreen)
                Log.d(TAG, "Firebase logout successful")
            } else {
                Log.d(TAG, "Firebase logout failed")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun checkForActiveSession() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            Log.d(TAG, "Valid session")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "Invalid session")
            isUserLoggedIn.value = false
        }
    }

    fun getUserData() {
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also { email ->
                emailId.value = email
            }
        }
    }

    fun listEstablishment() = getEstablishments()

    companion object {
        const val TAG = "HomeViewModel"
    }
}
