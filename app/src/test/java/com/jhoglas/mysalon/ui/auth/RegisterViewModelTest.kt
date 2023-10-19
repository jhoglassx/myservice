package com.jhoglas.mysalon.ui.auth

import com.jhoglas.mysalon.domain.usecase.AuthClientUseCase
import com.jhoglas.mysalon.ui.entity.State
import io.kotlintest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class RegisterViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val authClientUseCase = mockk<AuthClientUseCase>()
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RegisterViewModel(authClientUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `When name changed, state gets updated`() = runTest {
        val testValue = "John"
        viewModel.nameChange(testValue)

        viewModel.nameState.value.content shouldBe testValue
    }

    @Test
    fun `When email changed, state gets updated`() = runTest {
        val testValue = "john@example.com"
        viewModel.emailChange(testValue)

        viewModel.emailState.value.content shouldBe testValue
    }

    @Test
    fun `When password changed, state gets updated`() = runTest {
        val testValue = "password123"
        viewModel.passwordChange(testValue)

        viewModel.passwordState.value.content shouldBe testValue
    }

    @Test
    fun `When privacy policy check changed, state gets updated`() = runTest {
        val testValue = true
        viewModel.privacyPolicyCheckChange(testValue)

        viewModel.policyState.value.content shouldBe testValue
    }

    @Test
    fun `When loading screen state changes, it gets updated in registerState`() = runTest {
        val testValue = State.LOADING
        viewModel.loadingScreen(testValue)

        viewModel.registerState.value.state shouldBe testValue
    }

    @Test
    fun `When registerUserInFirebase is called, state updates based on use case success`() = runTest {
        val name = "John"
        val email = "john@example.com"
        val password = "password123"
        val useCaseResult = flow { emit(true) }

        coEvery { authClientUseCase.registerUserInFirebase(name, email, password) } returns useCaseResult

        viewModel.registerUserInFirebase(name, email, password)

        viewModel.registerState.value.state shouldBe State.SUCCESS
    }

    @Test
    fun `When registerUserInFirebase is called, state updates based on use case failure`() = runTest {
        val name = "John"
        val email = "john@example.com"
        val password = "password123"
        val exception = Exception("Test exception")

        coEvery {
            authClientUseCase.registerUserInFirebase(name, email, password)
        } throws exception

        viewModel.registerUserInFirebase(name, email, password)

        viewModel.registerState.value.state shouldBe State.ERROR
        viewModel.registerState.value.message shouldBe exception.message
    }
}
