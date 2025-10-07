package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class ResumeStep3Contract {
    data class State(
        // 희망 근무 위치 선택 기능에 대한 상태
        val preferLocationList: MutableList<String> = mutableListOf(),
        val showSelectLocationBottomSheet: Boolean = false,

        val totalPreferWorkCategoryList: Array<String> = arrayOf("식량작물", "채소", "과수", "특용작물", "화훼", "축산", "농기계작업", "기타"),
        val selectedPreferWorkCategoryList: List<String> = emptyList(),

        //지역 선택
        val regionList: List<String> = emptyList(),
        val subRegionList: List<String> = emptyList(),
        val selectedRegion: List<String> = emptyList(),
    ) : UiState

    sealed interface Event : UiEvent {
        // 희망 근무 위치 선택 기능에 대한 이벤트
        data class AddPreferLocation(val region: String): Event
        data class RemovePreferLocation(val region: String): Event
        data class ShowRegionBottomSheet(val openState: Boolean): Event

        data class SelectPreferWorkCategory(val category: String): Event
    }

    sealed interface Effect : UiEffect {
        data class FailToastMessage(val message: String):Effect
    }
}