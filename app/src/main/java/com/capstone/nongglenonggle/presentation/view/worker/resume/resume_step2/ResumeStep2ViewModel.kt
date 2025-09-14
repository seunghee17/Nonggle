package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
            is Step2Event.ShowDatePickerDialog -> {
                updateState(currentState.copy(showDatePickerDialog = event.dialogState))
            }
            is Step2Event.SetWorkPeriodRange -> {
                updateState(currentState.copy(isLongerThenMonth = event.isMonthOver))
            }
            is Step2Event.SetCareerDetail -> {
                updateState(currentState.copy(careerDetailContent = event.detail))
            }
        }
    }
}