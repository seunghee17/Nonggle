package com.capstone.nongglenonggle.presentation.view.login

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.login.SignInState
import javax.inject.Inject

class LoginContract @Inject constructor( ){
    data class State(
        val isLoading: Boolean = false,
        val signInState: SignInState = SignInState()
    ): UiState

    sealed class Event: UiEvent {
    }

    sealed class Effect: UiEffect {
        object NavigateToEnrollUser: Effect()
        object NavigateToHome: Effect()
        data class unAvailableToastmessage(val message: String): Effect()
    }
}