package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class ResumeStep4Contract {
    data class State(
        val preferWorkCategoryList: MutableList<String> = mutableListOf(),
        val totalPreferWorkCategoryList: Array<String> = arrayOf("식량작물", "채소", "과수", "")
    ) : UiState

    sealed interface Event : UiEvent {
        data class AddWorkCategoryChip(val value: String) :
            com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Event
        data class RemoveWorkCategoryChip(val value: String) :
            com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Event
    }

    sealed interface Effect : UiEffect {
        data class FailToastMessage(val message: String):
            com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Contract.Effect
    }
}