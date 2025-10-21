package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2

import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.model.worker.UserCareerListItemModel
import java.time.format.DateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.Period
import java.util.Locale
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Contract.Event as Step2Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Contract.Effect as Step2Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Contract.State as Step2State
import javax.inject.Inject

@HiltViewModel
class ResumeStep2ViewModel @Inject constructor() : BaseViewModel<Step2Event, Step2State, Step2Effect>(
    initialState = ResumeStep2Contract.State()
) {
    override fun handleEvent(event: Step2Event) {
        super.handleEvent(event)
        when(event) {
            is Step2Event.ShowCareerBottomSheet -> {
                updateState(currentState.copy(showCareerAddBottomSheet = event.bottomSheetState))
            }

            is Step2Event.SetCareerTitle -> {
                updateState(currentState.copy(careerTextFieldValue = event.title))
            }

            is Step2Event.ClearCareerTitle -> {
                updateState(currentState.copy(careerTextFieldValue = ""))
            }

            /// 경력 추가 bottomsheet 상태 이벤트
            is Step2Event.ShowStartDatePickerDialog -> {
                updateState(currentState.copy(showCalendarDialogStart = event.showDialog))
            }
            is Step2Event.ShowEndDatePickerDialog -> {
                updateState(currentState.copy(showCalendarDialogEnd = event.showDialog))
            }
            is Step2Event.SetWorkStartDate -> {
                val koreanLocale = Locale("ko", "KR")
                val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월", koreanLocale)
                updateState(
                    currentState.copy(
                        careerStartDate = event.date,
                        showCareerStartDate = event.date.format(formatter)
                    )
                )
            }
            is Step2Event.SetWorkEndDate -> {
                val startDate = currentState.careerStartDate ?: run {
                    postEffect(Step2Effect.ShowErrorToast("먼저 근무 시작일을 선택해 주세요."))
                    return
                }
                if(event.date < startDate) {
                    postEffect(Step2Effect.ShowErrorToast("근무 기간을 다시 확인해주세요."))
                    return
                }
                val koreanLocale = Locale("ko", "KR")
                val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월", koreanLocale)
                updateState(
                    currentState.copy(
                        careerEndDate = event.date,
                        showCareerEndDate = event.date.format(formatter)
                    )
                )
            }
            is Step2Event.SetWorkPeriodDate -> {
                updateState(currentState.copy(careerPeriodDay = event.date))
            }
            is Step2Event.SetWorkPeriodRange -> {
                updateState(currentState.copy(isLongerThenMonth = event.isMonthOver))
            }
            is Step2Event.SetCareerDetail -> {
                updateState(currentState.copy(careerDetailContent = event.detail))
            }
            is Step2Event.AddCareerItem -> {
                val newList = currentState.careerList.toMutableList()
                newList.add(parsingCareerItem())
                updateState(currentState.copy(careerList = newList))
                sumTotalCareer()
            }
            is Step2Event.RemoveCareerItem -> {
                val newList = currentState.careerList.toMutableList()
                newList.remove(event.item)
                sumTotalCareer()
                updateState(currentState.copy(careerList = newList))
            }
            is Step2Event.SetClearState -> {
                updateState(
                    currentState.copy(
                        careerTextFieldValue = "",
                        isLongerThenMonth = null,
                        careerStartDate = null,
                        showCareerStartDate = "근무시작일",
                        careerEndDate = null,
                        showCareerEndDate = "근무종료일",
                        careerPeriodDay = "근무 일 수",
                        careerDetailContent = ""
                    ))
            }
        }
    }

    private fun parsingCareerItem(): UserCareerListItemModel {
        var period: String = ""
        var periodDetail: String = ""
        var periodData: Period? = null
        if(currentState.isLongerThenMonth == true) {
            val periodDate = Period.between(currentState.careerStartDate, currentState.careerEndDate)
            periodData = periodDate
            period = "${periodDate.years}년 ${periodDate.months}개월"
            periodDetail = "${currentState.showCareerStartDate} ~ ${currentState.showCareerEndDate}"
        } else {
            period = currentState.careerPeriodDay
            periodDetail = currentState.careerPeriodDay
            periodData = Period.ofDays(currentState.careerPeriodDay.filter { it.isDigit() }.toInt())
        }
        val careerItem = UserCareerListItemModel(
            careerTitle = currentState.careerTextFieldValue,
            careerPeriodText = period,
            careerPeriodDetail = periodDetail,
            careerContent = currentState.careerDetailContent,
            careerPeriod = periodData
        )
        return careerItem
    }

    private fun sumTotalCareer() {
        val baseDate = LocalDate.of(2000,1,1)
        var currentDate = baseDate
        for(item in currentState.careerList) {
            currentDate = currentDate.plus(item.careerPeriod)
        }

        updateState(currentState.copy(
            totalPeriod = Period.between(baseDate, currentDate),
            totalPeriodParsing = "${Period.between(baseDate, currentDate).years}년 ${Period.between(baseDate, currentDate).months ?: 0}개월 ${Period.between(baseDate, currentDate).days ?: 0}일"
        ))
    }
}