package com.capstone.nongglenonggle.presentation.view.worker.resume

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class WorkerResumeContract {
    data class State(
        val isLoading: Boolean = true,

    ): UiState

    sealed class Event: UiEvent {

    }

    sealed class Effect: UiEffect {

    }
}