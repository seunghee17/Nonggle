package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step3

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class ResumeStep3Contract {
    data class State(
        val introduceDetail: String = "",
        val userPersonalityInput: String = "",
        val userPersonalities: List<String> = emptyList(),
        val additionalComment: String = "",
    ) : UiState

    sealed interface Event : UiEvent {
        data class SetIntroduceDetail(val detail: String) : Event
        object ClearIntroduceDetail: Event
        data class SetPersonalityType(val type: String): Event
        object ClearPersonalityType : Event
        data class SetAdditionalDetailComment(val comment: String): Event
    }

    sealed interface Effect : UiEffect {

    }
}