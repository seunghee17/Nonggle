package com.capstone.nongglenonggle.data.model.worker

import java.util.Date

data class ResumeStep2State(
    val isLoading: Boolean = false,
    val careerTextFieldValue: String = "",
    val careerAddBottomSheetState: CareerAddBottomSheetState = CareerAddBottomSheetState(),
    val careerList: List<ResumeStep2UserCareerListItem> = emptyList()
    )

//경력 추가 bottomsheet
data class CareerAddBottomSheetState(
    val isLongerThenMonth: Boolean? = null,
    val careerStartDate: Date? = null,
    val showCareerStartDate: String? = null,
    val careerEndDate: Date? = null,
    val showCareerEndDate: String? = null,
    val careerPeriodDay: String? = null,
    val careerDetailContent: String = "",
)

data class ResumeStep2UserCareerListItem(
    val careerTitle: String = "",
    val isLongerThenMonth: Boolean? = null,
    val careerStartDate: Date = Date(),
    val careerEndDate: Date?,
    val careerPeriodDay: String?,
    val careerDetailContent: String = "",
)
