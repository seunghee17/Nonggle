package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step4

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class ResumeStep4Contract {
    data class State(
        val preferWorkCategoryList: MutableList<String> = mutableListOf(),
        val totalPreferWorkCategoryList: Array<String> = arrayOf("식량작물", "채소", "과수", "")
    ) : UiState

    sealed interface Event : UiEvent {
        data class AddWorkCategoryChip(val value: String) : Event
        data class RemoveWorkCategoryChip(val value: String) : Event
    }

    sealed interface Effect : UiEffect {

    }
}