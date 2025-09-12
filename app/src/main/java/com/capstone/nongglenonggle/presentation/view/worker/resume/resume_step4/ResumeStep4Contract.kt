package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class ResumeStep4Contract {
    data class State(
        // 희망 근무 위치 선택 기능에 대한 상태
        val preferLocationList: MutableList<String> = mutableListOf(),
        val showSelectLocationBottomSheet: Boolean = false,

        val totalPreferWorkCategoryMap: HashMap<String, Boolean> = hashMapOf("식량작물" to false, "채소" to false, "과수" to false, "특용작물" to false, "화훼" to false, "축산" to false, "농기계작업" to false, "기타" to false),
    ) : UiState

    sealed interface Event : UiEvent {
        // 희망 근무 위치 선택 기능에 대한 이벤트
        data class AddPreferLocation(val region: String): Event
        data class RemovePreferLocation(val region: String): Event
        object ShowRegionBottomSheet: Event

        data class UpdateCategoryChipState(val key: String) :Event
    }

    sealed interface Effect : UiEffect {
        data class FailToastMessage(val message: String):Effect
    }
}