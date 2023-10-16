package com.jhoglas.mysalon.network

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jhoglas.mysalon.R
import com.jhoglas.mysalon.ui.auth.LoginViewModel
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import com.jhoglas.mysalon.ui.entity.UserData
import com.jhoglas.mysalon.ui.home.HomeViewModel
import com.jhoglas.mysalon.ui.navigation.AppRouter
import com.jhoglas.mysalon.ui.navigation.Screen
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.concurrent.CancellationException

class AuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
) {
    private val auth = Firebase.auth
    private val database = Firebase.database

    suspend fun loginWithEmail(email: String, password: String): ScreenState {
        Log.d(LoginViewModel.TAG, "Logging in...")
        return try {
            val resul = auth.signInWithEmailAndPassword(email, password).await()
            ScreenState(
                content = resul.user,
                state = if (resul.user != null) State.SUCCESS else State.ERROR
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Error Login", "Login failed: $e")
            if (e is CancellationException) throw e else null
            ScreenState(
                content = e.message,
                state = State.ERROR
            )
        }
    }

    suspend fun loginWithGoogle(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            Log.d("Error Login", "Login failed: $e")
            if (e is CancellationException) throw e else null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): ScreenState {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            val userData = UserData(
                name = user?.displayName ?: "",
                email = user?.email ?: "",
                phoneNumber = user?.phoneNumber ?: "",
                image = user?.photoUrl.toString(),
                dateCreate = Date().toString(),
                dateUpdate = Date().toString()
            )
            database.getReference("accounts")
                .child(auth.currentUser?.uid ?: "")
                .setValue(userData)

            ScreenState(
                content = userData,
                state = State.SUCCESS
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

    fun isLoggedUser() = auth.currentUser != null

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

    suspend fun registerUserInFirebase(
        name: String,
        email: String,
        password: String,

    ): ScreenState {
        val userData = UserData(
            name = name,
            email = email,
            phoneNumber = "31999999999",
            image = "",
            dateCreate = Date().toString(),
            dateUpdate = Date().toString()
        )
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            database.getReference("accounts")
                .child(auth.currentUser?.uid ?: "")
                .setValue(userData)
                .await()

            ScreenState(
                content = userData,
                state = State.SUCCESS,
                message = result.user?.uid ?: ""
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Error register", "Firebase register error: ${e.message}")
            if (e is CancellationException) throw e else null
            ScreenState(
                content = userData,
                state = State.ERROR,
                message = e.message ?: "Error"
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
            AppRouter.navigateTo(Screen.LoginScreen)
            Log.d(HomeViewModel.TAG, "Firebase logout successful")
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun checkForActiveSession(): Boolean {
        return if (auth.currentUser != null) {
            Log.d(LoginViewModel.TAG, "Valid session")
            true
        } else {
            Log.d(LoginViewModel.TAG, "Invalid session")
            false
        }
    }

    suspend fun getSignedInUser(): ScreenState {
        return try {
            val data = database.getReference("accounts")
                .child(auth.currentUser?.uid ?: "")
                .get()
                .await()

            val userData = UserData(
                name = data.child("name")?.value.toString(),
                email = data.child("email")?.value.toString(),
                phoneNumber = data.child("phoneNumber")?.value.toString(),
                image = data.child("image")?.value.toString(),
                dateCreate = data.child("dateCreate")?.value.toString(),
                dateUpdate = data.child("dateUpdate")?.value.toString(),
            )

            ScreenState(
                content = userData,
                state = State.SUCCESS
            )
        } catch (e: Exception) {
            e.printStackTrace()
            ScreenState(
                state = State.ERROR,
                message = e.message ?: "Error"
            )
        }
    }
}
