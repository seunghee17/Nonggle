package com.capstone.nongglenonggle.presentation.view.worker.resume

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.domain.entity.ResumeSummary

class WorkerResumeContract {
    data class State(
        val isLoading: Boolean = true,
        //val resumeResult: ResumeSummary,
    ): UiState

    sealed class Event: UiEvent {

    }

    sealed class Effect: UiEffect {

    }
}