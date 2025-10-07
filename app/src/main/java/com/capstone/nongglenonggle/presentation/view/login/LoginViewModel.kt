package com.capstone.nongglenonggle.presentation.view.login

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.model.login.SignInResult
import com.capstone.nongglenonggle.data.model.login.SignInState
import com.capstone.nongglenonggle.data.AppResult
import com.capstone.nongglenonggle.domain.usecase.GetUserAuthDataRepositoryUseCase
import com.capstone.nongglenonggle.presentation.view.signup.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.login.LoginContract.Effect as LoginEffect
import com.capstone.nongglenonggle.presentation.view.login.LoginContract.State as LoginState
import com.capstone.nongglenonggle.presentation.view.login.LoginContract.Event as LoginEvent

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserAuthDataRepositoryUseCase: GetUserAuthDataRepositoryUseCase,
    private val googleAuthClient: GoogleAuthClient,
) : BaseViewModel<LoginEvent, LoginState, LoginEffect>(initialState = LoginContract.State()) {

    override fun handleEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.KakaoLoginButtonClick -> {
                handleKakaoLogin()
            }
            is LoginEvent.GoogleLoginButtonClick -> {
                viewModelScope.launch {
                    val intentSender = googleAuthClient.signIn()
                    intentSender?.let {
                        postEffect(LoginContract.Effect.LaunchGoogleSignIn(it))
                    }
                }
            }
            is LoginEvent.OnGoogleSignInResult -> {
                viewModelScope.launch {
                    val signInResult = googleAuthClient.signInWithIntent(event.intent)
                    handleSignInResult(signInResult)
                }
            }
        }
    }

    private fun handleKakaoLogin() {
        postEffect(LoginEffect.ShowToastMessage("점검 중입니다. 다른 로그인 수단을 이용해주세요."))
    }

    fun handleSignInResult(result: SignInResult) {
        updateState(currentState.copy(signInState = SignInState(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage,
            isNewUser = result.isNewUser,
            userData = result.data
        )))
        if(result.data != null) {
            if(result.isNewUser == true) {
                postEffect(LoginEffect.NavigateToEnrollUser)
            } else if(result.isNewUser == false) {
                getUserLoginType()
            }
        }
    }

    private fun getUserLoginType() {
        viewModelScope.launch {
            val result = getUserAuthDataRepositoryUseCase.invoke()
            when(result) {
                is AppResult.Success -> {
                    if (UserType.valueOf(result.data.signUpType) == UserType.WORKER) {
                        postEffect(LoginEffect.NavigateToWorkerHome)
                    } else {
                        postEffect(LoginEffect.NavigateToEnrollUser)
                    }
                }
                is AppResult.Failure -> {
                    postEffect(LoginEffect.NavigateToEnrollUser)
                }
            }
        }
    }




}

