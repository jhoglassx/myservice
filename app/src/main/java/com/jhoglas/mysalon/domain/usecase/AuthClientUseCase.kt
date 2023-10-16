package com.jhoglas.mysalon.domain.usecase

import android.content.Intent
import android.content.IntentSender
import com.jhoglas.mysalon.ui.entity.ScreenState

interface AuthClientUseCase {

    suspend fun loginWithEmail(email: String, password: String): ScreenState
    suspend fun loginWithGoogle(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): ScreenState
    fun isLoggedUser(): Boolean
    suspend fun registerUserInFirebase(name: String, email: String, password: String): ScreenState
    suspend fun signOut()
    fun checkForActiveSession(): Boolean
    suspend fun getSignedInUser(): ScreenState
}
