package com.capstone.nongglenonggle.presentation.view.login

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.model.login.SignInResult
import com.capstone.nongglenonggle.data.model.login.SignInState
import com.capstone.nongglenonggle.domain.usecase.GetUserAuthDataRepositoryUseCase
import com.capstone.nongglenonggle.presentation.view.signup.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserAuthDataRepositoryUseCase: GetUserAuthDataRepositoryUseCase,
    private val googleAuthClient: GoogleAuthClient,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(initialState = LoginContract.State()) {

    override fun handleEvent(event: LoginContract.Event) {
        when(event) {
            is LoginContract.Event.KakaoLoginButtonClick -> {
                handleKakaoLogin()
            }
            is LoginContract.Event.GoogleLoginButtonClick -> {
                viewModelScope.launch {
                    val intentSender = googleAuthClient.signIn()
                    intentSender?.let {
                        postEffect(LoginContract.Effect.LaunchGoogleSignIn(it))
                    }
                }
            }
            is LoginContract.Event.OnGoogleSignInResult -> {
                viewModelScope.launch {
                    val signInResult = googleAuthClient.signInWithIntent(event.intent)
                    handleSignInResult(signInResult)
                }
            }
        }
    }

    private fun handleKakaoLogin() {
        postEffect(LoginContract.Effect.UnAvailableToastmessage("점검 중입니다. 다른 로그인 수단을 이용해주세요."))
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
                postEffect(LoginContract.Effect.NavigateToEnrollUser)
            } else if(result.isNewUser == false) {
                getUserLoginType()
            }
        }
    }

    fun getUserLoginType() {
        viewModelScope.launch {
            getUserAuthDataRepositoryUseCase.invoke()
                .onSuccess {
                    if(UserType.valueOf(it.signUpType) == UserType.WORKER) {
                        postEffect(LoginContract.Effect.NavigateToWorkerHome)
                    } else if(UserType.valueOf(it.signUpType) == UserType.MANAGER) {
                        postEffect(LoginContract.Effect.NavigateToFarmerHome)
                    } else {
                        postEffect(LoginContract.Effect.NavigateToEnrollUser)
                    }
                }
                .onFailure { e ->
                    val errorMessage = e.message ?: "사용자 가입 형식 로드에 실패했습니다."
                    updateState(currentState.copy(errorMessage = errorMessage))
                }
        }
    }




}

