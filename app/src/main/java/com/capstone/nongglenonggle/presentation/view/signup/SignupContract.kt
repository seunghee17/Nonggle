package com.capstone.nongglenonggle.presentation.view.signup

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import javax.inject.Inject

class SignupContract @Inject constructor() {
    data class State(
        val isLoading: Boolean = false,
        val SignUpStep: SignupStep = SignupStep.STEP1,
        val userSignupType: UserType = UserType.NONE
    ): UiState

    sealed class Event: UiEvent {
        data class SelectUseTypeBox(val type: UserType): Event()
    }

    sealed class Effect: UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null,
            val builder: NavOptionsBuilder.() -> Unit = {}
        ): Effect()
    }
}