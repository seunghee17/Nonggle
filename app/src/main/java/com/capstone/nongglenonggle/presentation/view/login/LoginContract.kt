package com.capstone.nongglenonggle.presentation.view.login

import android.content.Intent
import android.content.IntentSender
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.login.SignInState
import com.capstone.nongglenonggle.data.model.login.UserData

class LoginContract {
    data class State(
        val isLoading: Boolean = false,
        val signInState: SignInState = SignInState(
            userData = UserData(userId = "", userName = ""),
        ),
        val errorMessage: String = ""
    ): UiState

    sealed class Event: UiEvent {
        object KakaoLoginButtonClick: Event()
        object GoogleLoginButtonClick: Event()
        data class OnGoogleSignInResult(val intent: Intent): Event()
    }

    sealed class Effect: UiEffect {
        object NavigateToEnrollUser: Effect()
        object NavigateToWorkerHome: Effect()
        data class ShowToastMessage(val message: String): Effect()
        data class LaunchGoogleSignIn(val intentSender: IntentSender) : Effect()
    }
}