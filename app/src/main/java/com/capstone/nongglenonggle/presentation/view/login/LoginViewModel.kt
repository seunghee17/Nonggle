package com.capstone.nongglenonggle.presentation.view.login

import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.model.login.SignInResult
import com.capstone.nongglenonggle.data.model.login.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(initialState = LoginContract.State()) {

    override fun reduceState(event: LoginContract.Event) {
        when(event) {
            is LoginContract.Event.kakaoLoginButtonClick -> {
                postEffect(LoginContract.Effect.unAvailableToastmessage("점검 중입니다. 다른 로그인 수단을 이용해주세요."))
                postEffect(LoginContract.Effect.NavigateToHome)
            }
        }
    }

    fun onSingInResult(result: SignInResult) {
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
                postEffect(LoginContract.Effect.NavigateToHome)
            }
        }
    }
}

