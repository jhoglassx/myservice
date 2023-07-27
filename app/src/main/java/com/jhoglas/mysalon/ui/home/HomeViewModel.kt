package com.jhoglas.mysalon.ui.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.jhoglas.mysalon.ui.entity.NavigationItem
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen

class HomeViewModel() : ViewModel() {

    private val TAG = HomeViewModel::class.simpleName
    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            description = "Home Screen",
            itemId = "homeScreen",
            icon = Icons.Default.Home
        ),
        NavigationItem(
            title = "Settings",
            description = "Settings Screen",
            itemId = "settingsScreen",
            icon = Icons.Default.Settings
        ),
        NavigationItem(
            title = "Favorite",
            description = "Favorite Screen",
            itemId = "favoriteScreen",
            icon = Icons.Default.Favorite
        )
    )
    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val emailId: MutableLiveData<String> = MutableLiveData()

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
}
