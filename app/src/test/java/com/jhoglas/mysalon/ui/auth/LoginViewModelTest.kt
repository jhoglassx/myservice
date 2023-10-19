package com.jhoglas.mysalon.ui.auth

import android.content.Intent
import android.content.IntentSender
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.jhoglas.mysalon.domain.entity.UserDomainEntity
import com.jhoglas.mysalon.domain.usecase.AuthClientUseCase
import com.jhoglas.mysalon.ui.entity.ScreenState
import com.jhoglas.mysalon.ui.entity.State
import io.kotlintest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val authClientUseCaseMock: AuthClientUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = spyk(LoginViewModel(authClientUseCaseMock))
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `email is changed, it updates the loginStateEmail and calls validateFields`() = runTest {
        val email = "test@example.com"
        val expected = ScreenState(
            state = State.SUCCESS,
            content = email,
            message = "Email is valid"
        )

        viewModel.emailChange(email)

        viewModel.loginStateEmail.value shouldBe expected
        viewModel.allValidationsPassed.value shouldBe false
    }

    @Test
    fun `password is changed, it updates the loginStatePassword and calls validateFields`() = runTest {
        val password = "Password123"
        val expected = ScreenState(state = State.SUCCESS, content = password, message = "Password is valid")

        viewModel.passwordChange(password)

        viewModel.loginStatePassword.value shouldBe expected
        viewModel.allValidationsPassed.value shouldBe false
    }

    @Test
    fun `loadingScreen is called, it updates the loginState`() = runTest {
        val state = State.LOADING
        val expected = ScreenState(state = state, message = "")

        viewModel.loadingScreen(state)

        viewModel.loginState.value shouldBe expected
    }

    @Test
    fun `email and password are valid, allValidationsPassed is true`() = runTest {
        val email = "test@example.com"
        val password = "Password123"
        val expectedPassword = ScreenState(state = State.SUCCESS, content = password, message = "Password is valid")
        val expectedEmail = ScreenState(state = State.SUCCESS, content = email, message = "Email is valid")

        viewModel.emailChange(email)
        viewModel.passwordChange(password)

        viewModel.loginStateEmail.value shouldBe expectedEmail
        viewModel.loginStatePassword.value shouldBe expectedPassword
        viewModel.allValidationsPassed.value shouldBe true
    }

    @Test
    fun `email and password are invalid, allValidationsPassed is false`() = runTest {
        val email = "testexample.com"
        val password = "password"
        val expectedPassword = ScreenState(state = State.ERROR, content = password, message = "Password must contain one or more numbers")
        val expectedEmail = ScreenState(state = State.ERROR, content = email, message = "Invalid email")

        viewModel.emailChange(email)
        viewModel.passwordChange(password)

        viewModel.loginStateEmail.value shouldBe expectedEmail
        viewModel.loginStatePassword.value shouldBe expectedPassword
        viewModel.allValidationsPassed.value shouldBe false
    }

    @Test
    fun `email is invalid, loginStateEmail shows an error state`() = runTest {
        val email = "testexample.com"
        val expected = ScreenState(content = email, state = State.ERROR, message = "Invalid email")

        viewModel.emailChange(email)

        viewModel.loginStateEmail.value shouldBe expected
    }

    @Test
    fun `password is invalid, loginStatePassword shows an error state`() = runTest {
        val password = "pass"
        val expected = ScreenState(content = password, state = State.ERROR, message = "Password must contain 6 or more digits")

        viewModel.passwordChange(password)

        viewModel.loginStatePassword.value shouldBe expected
    }

    @Test
    fun `setUserData is called, it updates the userDataState on successful use case call`() = runTest {
        val user = UserDomainEntity()
        coEvery {
            authClientUseCaseMock.getSignedInUser()
        } returns flowOf(user)

        viewModel.setUserData()

        viewModel.userDataState.value shouldBe user
        coVerify(exactly = 1) { authClientUseCaseMock.getSignedInUser() }
    }

    @Test
    fun `setUserData is called and use case call fails, it updates the loginState with an error`() = runTest {
        coEvery {
            authClientUseCaseMock.getSignedInUser()
        } throws Exception("Error")

        viewModel.setUserData()

        viewModel.loginState.value shouldBe ScreenState(content = null, state = State.ERROR, message = "Error")
        coVerify(exactly = 1) { authClientUseCaseMock.getSignedInUser() }
    }

    @Test
    fun `checkForActiveSession is called and returns true then isUserLoggedIn should be true`() = runTest {
        coEvery { authClientUseCaseMock.checkForActiveSession() } returns flow { emit(true) }

        viewModel.checkForActiveSession()

        viewModel.isUserLoggedIn.value shouldBe true
    }

    @Test
    fun `checkForActiveSession is called and returns false then isUserLoggedIn should be false`() = runTest {
        coEvery { authClientUseCaseMock.checkForActiveSession() } returns flow { emit(false) }

        viewModel.checkForActiveSession()

        viewModel.isUserLoggedIn.value shouldBe false
    }

    @Test
    fun `checkForActiveSession is called and throws an error should silently handle it`() = runTest {
        coEvery { authClientUseCaseMock.checkForActiveSession() } throws Exception("Timeout Error")
        viewModel.checkForActiveSession()
    }

    @Test
    fun `signInWithIntent should set login state if sign-in is successful`() = runTest {
        val intentMock = mockk<Intent>()
        val loginStateMock = mockk<ScreenState>()
        coEvery { authClientUseCaseMock.signInWithIntent(intentMock) } returns loginStateMock

        viewModel.signInWithIntent(intentMock)

        coVerify(exactly = 1) { authClientUseCaseMock.signInWithIntent(any()) }
        viewModel.loginState.value shouldBe loginStateMock
    }

    @Test
    fun `signInWithIntent should set error screen state if auth client throws an exception`() = runTest {
        val intentMock = mockk<Intent>()
        val exceptionMock = mockk<Exception>(relaxed = true)
        coEvery { authClientUseCaseMock.signInWithIntent(intentMock) } throws exceptionMock

        viewModel.signInWithIntent(intentMock)

        coVerify(exactly = 1) { authClientUseCaseMock.signInWithIntent(any()) }
        viewModel.loginState.value?.state shouldBe State.ERROR
        viewModel.loginState.value?.message shouldBe exceptionMock.message
    }

    @Test
    fun `loginWithEmail is called and the use case call fails, it updates the loginState with an error`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val error = Exception("Error")
        coEvery { authClientUseCaseMock.loginWithEmail(any(), any()) } throws error

        viewModel.loginWithEmail(email, password)

        viewModel.loginState.value shouldBe ScreenState(content = null, state = State.ERROR, message = "Error")
        coVerify(exactly = 1) { authClientUseCaseMock.loginWithEmail(email, password) }
    }

    @Test
    fun `loginWithEmail is called and the use case call succeeds, it updates the loginState with the result`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val screenState = ScreenState(state = State.SUCCESS)
        coEvery { authClientUseCaseMock.loginWithEmail(any(), any()) } returns screenState

        viewModel.loginWithEmail(email, password)

        viewModel.loginState.value shouldBe screenState
        coVerify(exactly = 1) { authClientUseCaseMock.loginWithEmail(email, password) }
    }

    @Test
    fun `loginWithGoogle should launch sign-in intent sender successfully`() = runTest {
        val intentSender: IntentSender = mockk()
        val launcherMock: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> = mockk(relaxed = true)
        coEvery { authClientUseCaseMock.loginWithGoogle() } returns intentSender
        coEvery { launcherMock.launch(any()) } returns Unit

        viewModel.loginWithGoogle(launcherMock)

        coVerify(exactly = 1) { authClientUseCaseMock.loginWithGoogle() }
        coVerify(exactly = 1) { launcherMock.launch(any()) }
        viewModel.loginState.value?.message shouldBe ""
    }

    @Test
    fun `loginWithGoogle should set error screen state if auth client throws an exception`() = runTest {
        val exceptionMock = mockk<Exception>(relaxed = true)
        coEvery {
            authClientUseCaseMock.loginWithGoogle()
        } throws exceptionMock
        val launcherMock = mockk<ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>>(relaxed = true)

        viewModel.loginWithGoogle(launcherMock)

        coVerify { authClientUseCaseMock.loginWithGoogle() }
        verify(exactly = 0) { launcherMock.launch(any()) }
        viewModel.loginState.value?.state shouldBe State.ERROR
        viewModel.loginState.value?.message shouldBe exceptionMock.message
    }

    @Test
    fun `loginWithGoogle should do nothing if signInIntentSender is null`() = runTest {
        coEvery {
            authClientUseCaseMock.loginWithGoogle()
        } returns null
        val launcherMock = mockk<ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>>(relaxed = true)
        viewModel.loginWithGoogle(launcherMock)
        coVerify {
            authClientUseCaseMock.loginWithGoogle()
        }
        verify(exactly = 0) {
            launcherMock.launch(any())
        }
        viewModel.loginState.value?.message shouldBe ""
    }

    @Test
    fun `isLoggedUser returns the value from the use case`() = runTest {
        val expected = true
        every { authClientUseCaseMock.isLoggedUser() } returns expected

        val result = viewModel.isLoggedUser()

        result shouldBe expected
        verify { authClientUseCaseMock.isLoggedUser() }
    }

    @Test
    fun `signOut is called, it calls the use case's signOut`() = runTest {
        coEvery {
            authClientUseCaseMock.signOut()
        } returns Unit

        viewModel.signOut()

        coVerify(exactly = 1) { authClientUseCaseMock.signOut() }
    }

    @Test
    fun `signOut should call auth client use case and log success message`() = runTest {
        coEvery { authClientUseCaseMock.signOut() } just runs
        viewModel.signOut()
        coVerify(exactly = 1) { authClientUseCaseMock.signOut() }
    }

    @Test
    fun `signOut should log error message if auth client throws an exception`() = runTest {
        val exceptionMock = mockk<Exception>(relaxed = true)
        coEvery { authClientUseCaseMock.signOut() } throws exceptionMock
        viewModel.signOut()
        coVerify(exactly = 1) { authClientUseCaseMock.signOut() }
    }
}
