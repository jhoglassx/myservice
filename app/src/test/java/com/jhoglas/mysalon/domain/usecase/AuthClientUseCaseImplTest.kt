package com.jhoglas.mysalon.domain.usecase

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jhoglas.mysalon.data.remote.AuthDataSource
import com.jhoglas.mysalon.data.remote.UserRemoteEntity
import com.jhoglas.mysalon.data.remote.toDomain
import com.jhoglas.mysalon.ui.entity.State
import io.kotlintest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.util.concurrent.CancellationException

class AuthClientUseCaseImplTest {
    private lateinit var useCase: AuthClientUseCaseImpl
    private val context = mockk<Context>()
    private val oneTapClient = mockk<SignInClient>()
    private val auth = mockk<FirebaseAuth>()
    private val authDataSource = mockk<AuthDataSource>()

    private val userRemoteEntityMock = UserRemoteEntity(
        name = "Test Name",
        email = "test@mail.com",
        phoneNumber = "123456789",
        image = "test.png",
        dateUpdate = "2022-02-13",
        dateCreate = "2022-02-13"
    )

    @Before
    fun setUp() {
        useCase = AuthClientUseCaseImpl(context, oneTapClient, auth, authDataSource)
    }

    @Test
    fun `loginWithEmail success`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val expectedUser: FirebaseUser = mockk()

        val authResult: AuthResult = mockk {
            every { user } returns expectedUser
        }
        val taskMock: Task<AuthResult> = mockk {
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { result } returns authResult
            every { exception } returns null
            every { isCanceled } returns false
        }

        coEvery { auth.signInWithEmailAndPassword(email, password) } returns taskMock

        val screenState = useCase.loginWithEmail(email, password)

        screenState.content shouldBe expectedUser
        screenState.state shouldBe State.SUCCESS
    }

    @Test
    fun `loginWithEmail error`() = runTest {
        val email = "test@example.com"
        val password = "password"

        val authResult: AuthResult = mockk {
            every { user } returns null
        }
        val taskMock: Task<AuthResult> = mockk {
            every { isSuccessful } returns false
            every { isComplete } returns true
            every { result } returns authResult
            every { exception } returns null
            every { isCanceled } returns false
        }

        coEvery { auth.signInWithEmailAndPassword(email, password) } returns taskMock

        val screenState = useCase.loginWithEmail(email, password)

        screenState.content shouldBe null
        screenState.state shouldBe State.ERROR
    }

    @Ignore // TODO adjust this test
    @Test
    fun `loginWithGoogle - success`() = runTest {
        val pendingIntent: PendingIntent = mockk(relaxed = true)
        val intentSender: IntentSender = mockk(relaxed = true)
        val successResult: BeginSignInResult = mockk(relaxed = true)
        val successResultTask: Task<BeginSignInResult> = mockk(relaxed = true)

        coEvery { oneTapClient.beginSignIn(any()) } returns successResultTask
        coEvery { successResultTask.await() } returns successResult
        every { successResult.pendingIntent } returns pendingIntent
        every { pendingIntent.intentSender } returns intentSender

        val result = useCase.loginWithGoogle()

        result shouldBe intentSender
    }

    @Test
    fun `loginWithGoogle - non-CancellationException`() = runTest {
        val signInRequest: BeginSignInRequest = mockk()

        coEvery { oneTapClient.beginSignIn(signInRequest) } throws RuntimeException()

        val result = useCase.loginWithGoogle()

        result shouldBe null
    }

    @Ignore // TODO adjust this test
    @Test(expected = CancellationException::class)
    fun `loginWithGoogle - CancellationException`() = runTest {
        val signInRequest: BeginSignInRequest = mockk()

        coEvery { oneTapClient.beginSignIn(signInRequest) } throws CancellationException()

        useCase.loginWithGoogle()
    }

    @Test
    fun `signInWithIntent success`() = runTest {
        val expectedUser: FirebaseUser = mockk {
            every { uid } returns "testId"
            every { displayName } returns "Test Name"
            every { email } returns "test@mail.com"
            every { phoneNumber } returns "123456789"
            every { photoUrl.toString() } returns "test.png"
        }
        val intent: Intent = mockk()

        val idToken = "testIdToken"

        val signInCredential: SignInCredential = mockk {
            every { googleIdToken } returns idToken
        }
        every { oneTapClient.getSignInCredentialFromIntent(intent) } returns signInCredential

        val authResult: AuthResult = mockk {
            every { user } returns expectedUser
        }

        val taskMock: Task<AuthResult> = mockk {
            every { isComplete } returns true
            every { result } returns authResult
            every { exception } returns null
            every { isCanceled } returns false
        }
        every { auth.currentUser } returns expectedUser
        coEvery { authDataSource.setUser(any(), any()) } returns flowOf(true)

        coEvery { auth.signInWithCredential(any()) } returns taskMock

        val screenState = useCase.signInWithIntent(intent)

        screenState.state shouldBe State.SUCCESS
    }

    @Test
    fun `signInWithIntent error`() = runTest {
        val intent: Intent = mockk()

        val idToken = "testIdToken"

        val signInCredential: SignInCredential = mockk {
            every { googleIdToken } returns idToken
        }
        every { oneTapClient.getSignInCredentialFromIntent(intent) } returns signInCredential

        val mockTask: Task<AuthResult> = mockk {
            every { isSuccessful } returns false
        }

        val authUserResult: AuthResult = mockk {
            every { user } returns null
        }

        val taskMock: Task<AuthResult> = mockk {
            every { isComplete } returns true
            every { result } returns authUserResult
            every { exception } returns null
            every { isCanceled } returns false
            every { addOnCompleteListener(any()) } returns mockTask
        }

        every { auth.currentUser } returns null
        coEvery { authDataSource.setUser(any(), any()) } returns flowOf(false)

        coEvery { auth.signInWithCredential(any()) } returns taskMock

        val screenState = useCase.signInWithIntent(intent)

        screenState.state shouldBe State.ERROR
    }

    @Test
    fun `isLoggedUser true`() {
        every { auth.currentUser } returns mockk()
        useCase.isLoggedUser() shouldBe true
    }

    @Test
    fun `isLoggedUser false`() {
        every { auth.currentUser } returns null
        useCase.isLoggedUser() shouldBe false
    }

    @Test
    fun `checkForActiveSession true`() = runTest {
        val user = mockk<FirebaseUser>()

        every { auth.currentUser } returns user

        useCase.checkForActiveSession().collect {
            it shouldBe true
        }
    }

    @Test
    fun `checkForActiveSession false`() = runTest {
        every { auth.currentUser } returns null

        useCase.checkForActiveSession().collect {
            it shouldBe false
        }
    }

    @Test
    fun `getSignedInUser success`() = runTest {
        val uid = "user-id"
        val firebaseUser: FirebaseUser = mockk()
        every { firebaseUser.uid } returns uid

        every { auth.currentUser } returns firebaseUser

        coEvery { authDataSource.getUser(uid) } returns flowOf(userRemoteEntityMock)

        val resultFlow = useCase.getSignedInUser()

        resultFlow.collect {
            it shouldBe userRemoteEntityMock.toDomain()
        }

        verify { auth.currentUser }
        verify { firebaseUser.uid }
        coVerify { authDataSource.getUser(uid) }
        confirmVerified(auth, firebaseUser, authDataSource)
    }
}
