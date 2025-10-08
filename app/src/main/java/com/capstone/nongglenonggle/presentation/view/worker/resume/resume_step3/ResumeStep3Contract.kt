package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class ResumeStep3Contract {
    data class State(
        // 희망 근무 위치 선택 기능에 대한 상태
        val isLocationBottomSheetLoading: Boolean = false,
        val preferLocationList: List<String> = mutableListOf(),
        val showSelectLocationBottomSheet: Boolean = false,
        //지역 선택
        val regionList: List<String> = emptyList(),
        val subRegionList: List<String> = emptyList(),
        val selectedParentRegion: String = "",
        val selectedSubRegion: String = "",

        //step3 화면 상태
        val totalPreferWorkCategoryList: Array<String> = arrayOf("식량작물", "채소", "과수", "특용작물", "화훼", "축산", "농기계작업", "기타"),
        val selectedPreferWorkCategoryList: List<String> = emptyList(),

        ) : UiState

    sealed interface Event : UiEvent {
        // 희망 근무 위치 선택 기능에 대한 이벤트
        data class AddPreferLocation(val subRegion: String): Event
        data class RemovePreferLocation(val region: String): Event
        data class ShowRegionBottomSheet(val isOpen: Boolean): Event
        data class GetSubRegionList(val parentRegion: String): Event
        object ClearSheetState: Event

        data class SelectPreferWorkCategory(val category: String): Event
    }

    sealed interface Effect : UiEffect {
        data class FailToastMessage(val message: String):Effect
    }
}