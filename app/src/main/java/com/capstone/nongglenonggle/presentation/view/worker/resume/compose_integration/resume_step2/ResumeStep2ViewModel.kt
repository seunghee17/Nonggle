package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step2

import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step2.ResumeStep2Contract.Effect as effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration.resume_step2.ResumeStep2Contract.Event as event
import javax.inject.Inject

class ResumeStep2ViewModel @Inject constructor() : BaseViewModel<ResumeStep2Contract.Event, ResumeStep2Contract.State, ResumeStep2Contract.Effect>(
    initialState = ResumeStep2Contract.State()
) {
    override fun handleEvent(event: event) {
        super.handleEvent(event)
        when(event) {
            is event.SetCareerTitle -> {
                updateState(currentState.copy(careerTextFieldValue = event.title))
            }

            is event.ClearCareerTitle -> {
                updateState(currentState.copy(careerTextFieldValue = ""))
            }

            is event.SetWorkPeriodRange -> {
                updateState(currentState.copy(
                    careerAddBottomSheetState = currentState.careerAddBottomSheetState.copy(isLongerThenMonth = event.isMonthOver)
                ))
            }
            is event.SetCareerDetail -> {
                updateState(currentState.copy(
                    careerAddBottomSheetState = currentState.careerAddBottomSheetState.copy(careerDetailContent = event.detail)
                ))
            }
        }
    }
}