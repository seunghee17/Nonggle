package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.worker.ResumeStep2UserCareerListItem
import java.util.Date

class ResumeStep2Contract {
    data class State(
        val isLoading: Boolean = false,
        val showCareerAddBottomSheet: Boolean = false,
        val careerList: List<ResumeStep2UserCareerListItem> = emptyList(),

        //bottomsheet의 상태관련 변수
        val showDatePickerDialog: Boolean = false,
        val setStartRangeDate: Boolean = false,
        val careerTextFieldValue: String = "",
        val isLongerThenMonth: Boolean? = null,
        val careerStartDate: Date? = null,
        val showCareerStartDate: String? = null,
        val careerEndDate: Date? = null,
        val showCareerEndDate: String? = null,
        val careerPeriodDay: String? = null,
        val careerDetailContent: String = "",
    ) : UiState

    sealed interface Event : UiEvent {
        data class ShowCareerBottomSheet(val bottomSheetState: Boolean): Event
        data class SetCareerTitle(val title: String) : Event
        object ClearCareerTitle : Event

        //bottomsheet 이벤트
        data class ShowDatePickerDialog(val dialogState: Boolean): Event
        data class SetWorkPeriodRange(val isMonthOver: Boolean): Event
        data class SetCareerDetail(val detail: String): Event
        data class SetWorkHistoryRangeType(val isStart: Boolean): Event
        data class SetWorkHistoryDate(val historyDate: Date) : Event
    }

    sealed interface Effect : UiEffect {

    }
}