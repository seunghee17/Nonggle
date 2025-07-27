package com.capstone.nongglenonggle.presentation.view.onboarding

import android.content.Context
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import javax.inject.Inject

class StartNonggleContract @Inject constructor( ){
    data class State(
        val isLoading: Boolean = false,
    ): UiState

    sealed class Event: UiEvent {
        data class kakaoLoginButtonClickEvent(val context: Context): Event()
        data class googleLoginButtonClickEvent(val context: Context): Event()
        data class signUpWithPhoneNumberButtonClickEvent(val context: Context): Event()
        data class loginButtonClickEvent(val context: Context): Event()
    }

    sealed class Effect: UiEffect {
        object NavigateToSignUp: Effect()
        object NavigateToLogin: Effect()
        data class unAvailableToastmessage(val message: String): Effect()
    }
}