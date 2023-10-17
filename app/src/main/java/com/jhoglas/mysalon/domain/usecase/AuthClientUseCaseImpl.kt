package com.jhoglas.mysalon.domain.usecase

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.data.remote.AuthDataSource
import com.jhoglas.mysalon.data.remote.toDomain
import com.jhoglas.mysalon.domain.entity.UserDomainEntity
import com.jhoglas.mysalon.domain.entity.toRemote
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.jetbrains.annotations.VisibleForTesting
import java.util.Date
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthClientUseCaseImpl @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth,
    private val authDataSource: AuthDataSource
) : AuthClientUseCase {

    override suspend fun loginWithEmail(email: String, password: String): ScreenState {
        val resul = auth.signInWithEmailAndPassword(email, password).await()
        return ScreenState(
            content = resul.user,
            state = if (resul.user != null) State.SUCCESS else State.ERROR
        )
    }

    override suspend fun loginWithGoogle(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            if (e is CancellationException) throw e else null
        }

        return result?.pendingIntent?.intentSender
    }

    override suspend fun signInWithIntent(intent: Intent): ScreenState {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            val userData = UserDomainEntity(
                name = user?.displayName ?: "",
                email = user?.email ?: "",
                phoneNumber = user?.phoneNumber ?: "",
                image = user?.photoUrl.toString(),
                dateCreate = Date().toString(),
                dateUpdate = Date().toString()
            ).toRemote()

            auth.currentUser?.let {
                authDataSource.setUser(it.uid, userData)
            }

            ScreenState(
                content = userData,
                state = if (auth.currentUser == null) State.ERROR else State.SUCCESS
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Error Login", "Login failed: $e")
            if (e is CancellationException) throw e else null

            ScreenState(
                message = e.message ?: "Error"
            )
        }
    }

    override fun isLoggedUser(): Boolean = auth.currentUser != null

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    override suspend fun registerUserInFirebase(
        name: String,
        email: String,
        password: String,
    ): Flow<Boolean> = flow {
        val user = UserDomainEntity(
            name = name,
            email = email,
            phoneNumber = "31999999999",
            image = "",
            dateCreate = Date().toString(),
            dateUpdate = Date().toString()
        ).toRemote()

        val result = auth.createUserWithEmailAndPassword(email, password).await()

        result.user?.uid?.let {
            authDataSource.setUser(it, user)
        }
    }

    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
            AppRouter.navigateTo(Screen.LoginScreen)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    override fun checkForActiveSession(): Flow<Boolean> = flow {
        if (auth.currentUser != null) {
            emit(true)
        } else {
            emit(false)
        }
    }

    override suspend fun getSignedInUser(): Flow<UserDomainEntity> = flow {
        val id = auth.currentUser?.uid ?: ""
        authDataSource.getUser(id).collect {
            emit(it.toDomain())
        }
    }

    @VisibleForTesting
    fun buildSignInRequestTest() = buildSignInRequest()
}
