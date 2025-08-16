package com.capstone.nongglenonggle.presentation.view.login

import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.model.login.SignInResult
import com.capstone.nongglenonggle.data.model.login.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(initialState = LoginContract.State()) {

    override fun reduceState(event: LoginContract.Event) {
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
                setEffect(LoginContract.Effect.NavigateToEnrollUser)
            } else if(result.isNewUser == false) {
                setEffect(LoginContract.Effect.NavigateToHome)
            }
        }
    }
}

