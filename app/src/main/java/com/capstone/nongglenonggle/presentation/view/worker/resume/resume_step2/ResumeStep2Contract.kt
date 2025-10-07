package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import com.capstone.nongglenonggle.data.model.worker.UserCareerListItemModel
import java.time.LocalDate
import java.time.Period

class ResumeStep2Contract {
    data class State(
        val isLoading: Boolean = false,
        val showCareerAddBottomSheet: Boolean = false,
        val careerList: List<UserCareerListItemModel> = emptyList(),
        val totalPeriod: Period? = null,
        val totalPeriodParsing: String = "총 0년 0개월 0일",

        //bottomsheet의 상태관련 변수
        val showCalendarDialogStart: Boolean = false,
        val showCalendarDialogEnd: Boolean = false,


        val careerTextFieldValue: String = "",
        val isLongerThenMonth: Boolean? = null,

        val careerStartDate: LocalDate? = null,
        val showCareerStartDate: String = "근무시작일",
        val careerEndDate: LocalDate? = null,
        val showCareerEndDate: String = "근무종료일",
        //일 수 선택
        val careerPeriodDay: String = "근무 일 수 선택",

        val careerDetailContent: String = "",
    ) : UiState

    sealed interface Event : UiEvent {
        data class ShowCareerBottomSheet(val bottomSheetState: Boolean): Event
        data class SetCareerTitle(val title: String) : Event
        object ClearCareerTitle : Event
        data class RemoveCareerItem(val item: UserCareerListItemModel): Event

        //bottomsheet 이벤트
        data class ShowStartDatePickerDialog(val showDialog: Boolean): Event
        data class ShowEndDatePickerDialog(val showDialog: Boolean): Event
        data class SetWorkPeriodRange(val isMonthOver: Boolean): Event
        data class SetWorkStartDate(val date: LocalDate): Event
        data class SetWorkEndDate(val date: LocalDate): Event
        data class SetWorkPeriodDate(val date: String): Event
        data class SetCareerDetail(val detail: String): Event
        object AddCareerItem: Event
        object SetClearState: Event
    }

    sealed interface Effect : UiEffect {
        data class ShowErrorToast(val message: String) : Effect
    }
}