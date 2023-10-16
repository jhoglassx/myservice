package com.jhoglas.mysalon.domain.usecase

import android.content.Intent
import android.content.IntentSender
import com.jhoglas.mysalon.domain.entity.UserDomainEntity
import com.jhoglas.mysalon.ui.entity.ScreenState
import kotlinx.coroutines.flow.Flow

interface AuthClientUseCase {

    suspend fun loginWithEmail(email: String, password: String): ScreenState
    suspend fun loginWithGoogle(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): ScreenState
    fun isLoggedUser(): Boolean
    suspend fun registerUserInFirebase(name: String, email: String, password: String): Flow<Boolean>
    suspend fun signOut()
    fun checkForActiveSession(): Flow<Boolean>
    suspend fun getSignedInUser(): Flow<UserDomainEntity>
}
