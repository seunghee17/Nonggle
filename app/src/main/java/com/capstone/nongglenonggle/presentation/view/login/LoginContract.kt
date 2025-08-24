package com.capstone.nongglenonggle.presentation.view.login

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.login.SignInState
import com.capstone.nongglenonggle.data.model.login.UserData
import javax.inject.Inject

class LoginContract @Inject constructor( ){
    data class State(
        val isLoading: Boolean = false,
        val signInState: SignInState = SignInState(
            userData = UserData(userId = "", userName = ""),
        )
    ): UiState

    sealed class Event: UiEvent {
        object kakaoLoginButtonClick: Event()
    }

    sealed class Effect: UiEffect {
        object NavigateToEnrollUser: Effect()
        object NavigateToFarmerHome: Effect()
        object NavigateToWorkerHome: Effect()
        data class unAvailableToastmessage(val message: String): Effect()
    }
}