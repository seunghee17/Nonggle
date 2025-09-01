package com.capstone.nongglenonggle.viewmodel

import android.content.Intent
import android.content.IntentSender
import app.cash.turbine.test
import com.capstone.nongglenonggle.data.model.login.SignInResult
import com.capstone.nongglenonggle.data.model.login.UserData
import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.domain.usecase.GetUserAuthDataRepositoryUseCase
import com.capstone.nongglenonggle.presentation.view.login.GoogleAuthClient
import com.capstone.nongglenonggle.presentation.view.login.LoginContract
import com.capstone.nongglenonggle.presentation.view.login.LoginViewModel
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() { Dispatchers.setMain(dispatcher) }
    @After
    fun tearDown() { Dispatchers.resetMain() }

    // fakes
    private val mockUseCase = mockk<GetUserAuthDataRepositoryUseCase>()
    private val mockGoogle = mockk<GoogleAuthClient>()

    private fun vm() = LoginViewModel(mockUseCase, mockGoogle)

    @Test
    fun `카카오 클릭 시 점검 토스트 이펙트 발행`(): Unit = runTest {
        val vm = vm()
        vm.effect.test {
            vm.handleEvent(LoginContract.Event.KakaoLoginButtonClick)
            val e = awaitItem()
            assertTrue(e is LoginContract.Effect.UnAvailableToastmessage)
        }
    }

    @Test
    fun `테스트 2`(): Unit = runTest {
        val sender = mockk<IntentSender>()
        coEvery { mockGoogle.signIn() } returns sender

        val vm = vm()
        vm.effect.test {
            vm.handleEvent(LoginContract.Event.GoogleLoginButtonClick)
            advanceUntilIdle()
            val e = awaitItem()
            assertTrue(e is LoginContract.Effect.LaunchGoogleSignIn)
            assertEquals(sender, (e as LoginContract.Effect.LaunchGoogleSignIn).intentSender)
        }
    }

    @Test
    fun `구글 로그인 결과 - 신규 유저면 Enroll로 네비게이션`(): Unit = runTest {
        val intent = mockk<Intent>()
        val signInResult = SignInResult(
            data = UserData(userId="u1", userName="n1"),
            errorMessage = null,
            isNewUser = true
        )
        coEvery { mockGoogle.signInWithIntent(intent) } returns signInResult

        val vm = vm()
        vm.effect.test {
            vm.handleEvent(LoginContract.Event.OnGoogleSignInResult(intent))
            advanceUntilIdle()
            assertTrue(awaitItem() is LoginContract.Effect.NavigateToEnrollUser)
        }
    }

    @Test
    fun `구글 로그인 결과 - 기존 유저 + WORKER면 WorkerHome으로`() = runTest {
        val intent = mockk<Intent>()
        val signInResult = SignInResult(
            data = UserData(userId="u1", userName="n1"),
            errorMessage = null,
            isNewUser = false
        )
        coEvery { mockGoogle.signInWithIntent(intent) } returns signInResult
        coEvery { mockUseCase.invoke() } returns Result.success(UserDataClass(signUpType = "WORKER"))

        val vm = vm()
        vm.effect.test {
            vm.handleEvent(LoginContract.Event.OnGoogleSignInResult(intent))
            advanceUntilIdle()
            assertTrue(awaitItem() is LoginContract.Effect.NavigateToWorkerHome)
        }
    }

    @Test
    fun `가입 유형 로드 실패 시 errorMessage 갱신`() = runTest {
        coEvery { mockUseCase.invoke() } returns Result.failure(IllegalStateException("fail"))

        val vm = vm()
        vm.getUserLoginType()
        advanceUntilIdle()

        val state = vm.uiState.value
        assertEquals("fail", state.errorMessage)
    }

    @Test
    fun `handleSignInResult는 signInState를 갱신한다`() = runTest {
        val vm = vm()
        val result = SignInResult(
            data = UserData(userId="u1", userName="n1"),
            errorMessage = null,
            isNewUser = null
        )
        vm.handleSignInResult(result)
        val s = vm.uiState.value.signInState
        assertTrue(s.isSignInSuccessful)
        assertNull(s.signInError)
        assertEquals("u1", s.userData?.userId)
    }
}
