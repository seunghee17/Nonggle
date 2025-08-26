package com.capstone.nongglenonggle.presentation.view.splash

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class SplashContract {
    data class State(
        val isLoading: Boolean = false,
    ): UiState

    sealed class Event: UiEvent {
    }

    sealed class Effect: UiEffect {
        object NavigateToFarmerHome: Effect()
        object NavigateToWorkerHome: Effect()
        object NavigateToLogin: Effect()
    }
}