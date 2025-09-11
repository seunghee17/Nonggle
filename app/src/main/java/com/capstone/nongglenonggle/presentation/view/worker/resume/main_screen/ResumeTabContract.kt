package com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class ResumeTabContract {
    data class State(
      val isLoading: Boolean = false
    ): UiState

    sealed interface Event: UiEvent {

    }

    sealed interface Effect : UiEffect {

    }
}