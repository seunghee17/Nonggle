package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step2

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.worker.CareerAddBottomSheetState
import com.capstone.nongglenonggle.data.model.worker.ResumeStep2UserCareerListItem

class ResumeStep2Contract {
    data class State(
        val isLoading: Boolean = false,
        val careerTextFieldValue: String = "",
        val careerAddBottomSheetState: CareerAddBottomSheetState = CareerAddBottomSheetState(),
        val careerList: List<ResumeStep2UserCareerListItem> = emptyList()
    ) : UiState

    sealed interface Event : UiEvent {
        data class SetCareerTitle(val title: String) : Event
        object ClearCareerTitle : Event
        data class SetWorkPeriodRange(val isMonthOver: Boolean): Event
        data class SetCareerDetail(val detail: String): Event
    }

    sealed interface Effect : UiEffect {

    }
}