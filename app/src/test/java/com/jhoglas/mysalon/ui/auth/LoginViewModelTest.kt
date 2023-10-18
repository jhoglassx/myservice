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
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    fun `when email is changed, it updates the loginStateEmail and calls validateFields`() = runTest {
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
    fun `when password is changed, it updates the loginStatePassword and calls validateFields`() = runTest {
        val password = "Password123"
        val expected = ScreenState(state = State.SUCCESS, content = password, message = "Password is valid")

        viewModel.passwordChange(password)

        viewModel.loginStatePassword.value shouldBe expected
        viewModel.allValidationsPassed.value shouldBe false
    }

    @Test
    fun `when loadingScreen is called, it updates the loginState`() = runTest {
        val state = State.LOADING
        val expected = ScreenState(state = state, message = "")

        viewModel.loadingScreen(state)

        viewModel.loginState.value shouldBe expected
    }

    @Test
    fun `when email and password are valid, allValidationsPassed is true`() = runTest {
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
    fun `when email and password are invalid, allValidationsPassed is false`() = runTest {
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
    fun `when email is invalid, loginStateEmail shows an error state`() = runTest {
        val email = "testexample.com"
        val expected = ScreenState(content = email, state = State.ERROR, message = "Invalid email")

        viewModel.emailChange(email)

        viewModel.loginStateEmail.value shouldBe expected
    }

    @Test
    fun `when password is invalid, loginStatePassword shows an error state`() = runTest {
        val password = "pass"
        val expected = ScreenState(content = password, state = State.ERROR, message = "Password must contain 6 or more digits")

        viewModel.passwordChange(password)

        viewModel.loginStatePassword.value shouldBe expected
    }

    @Test
    fun `when setUserData is called, it updates the userDataState on successful use case call`() = runTest {
        val user = UserDomainEntity()
        coEvery {
            authClientUseCaseMock.getSignedInUser()
        } returns flowOf(user)

        viewModel.setUserData()

        viewModel.userDataState.value shouldBe user
        coVerify(exactly = 1) { authClientUseCaseMock.getSignedInUser() }
    }

    @Test
    fun `when setUserData is called and use case call fails, it updates the loginState with an error`() = runTest {
        coEvery {
            authClientUseCaseMock.getSignedInUser()
        } throws Exception("Error")

        viewModel.setUserData()

        viewModel.loginState.value shouldBe ScreenState(content = null, state = State.ERROR, message = "Error")
        coVerify(exactly = 1) { authClientUseCaseMock.getSignedInUser() }
    }

    @Test
    fun `when checkForActiveSession is called, it updates the isUserLoggedIn MutableLiveData with the use case's value`() = runTest {
        val isUserLoggedIn = true
        coEvery {
            authClientUseCaseMock.checkForActiveSession()
        } returns flowOf(isUserLoggedIn)

        viewModel.checkForActiveSession()

        viewModel.isUserLoggedIn.value shouldBe isUserLoggedIn

        coVerify(exactly = 1) { authClientUseCaseMock.checkForActiveSession() }
    }

    @Test
    fun `when signInWithIntent is called, it updates the loginState with the use case's value`() = runTest {
        val intent: Intent = mockk()
        val screenState = ScreenState(state = State.EMPTY)
        coEvery { authClientUseCaseMock.signInWithIntent(any()) } returns screenState

        viewModel.signInWithIntent(intent)

        viewModel.loginState.value shouldBe screenState
        coVerify(exactly = 1) { authClientUseCaseMock.signInWithIntent(any()) }
    }

    @Test
    fun `when loginWithEmail is called and the use case call fails, it updates the loginState with an error`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val error = Exception("Error")
        coEvery { authClientUseCaseMock.loginWithEmail(any(), any()) } throws error

        viewModel.loginWithEmail(email, password)

        viewModel.loginState.value shouldBe ScreenState(content = null, state = State.ERROR, message = "Error")
        coVerify(exactly = 1) { authClientUseCaseMock.loginWithEmail(email, password) }
    }

    @Test
    fun `when loginWithEmail is called and the use case call succeeds, it updates the loginState with the result`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val screenState = ScreenState(state = State.SUCCESS)
        coEvery { authClientUseCaseMock.loginWithEmail(any(), any()) } returns screenState

        viewModel.loginWithEmail(email, password)

        viewModel.loginState.value shouldBe screenState
        coVerify(exactly = 1) { authClientUseCaseMock.loginWithEmail(email, password) }
    }

    @Test
    fun `when loginWithGoogle is called, it launches the provided launcher with the IntentSenderRequest`() = runTest {
        val intentSender: IntentSender = mockk()
        val launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> = mockk(relaxed = true)
        coEvery { authClientUseCaseMock.loginWithGoogle() } returns intentSender
        coEvery { launcher.launch(any()) } returns Unit

        viewModel.loginWithGoogle(launcher)

        coVerify(exactly = 1) { launcher.launch(any()) }
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
    fun `when signOut is called, it calls the use case's signOut`() = runTest {
        coEvery {
            authClientUseCaseMock.signOut()
        } returns Unit

        viewModel.signOut()

        coVerify(exactly = 1) { authClientUseCaseMock.signOut() }
    }
}
