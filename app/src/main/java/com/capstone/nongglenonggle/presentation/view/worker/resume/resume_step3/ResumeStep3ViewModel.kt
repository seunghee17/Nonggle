package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Effect as Step3Effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Event as Step3Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.State as Step3State

@HiltViewModel
class ResumeStep3ViewModel @Inject constructor() :
    BaseViewModel<Step3Event, Step3State, Step3Effect>(
        initialState = ResumeStep3Contract.State()
    ) {
    override fun handleEvent(event: Step3Event) {
        super.handleEvent(event)
        when(event) {
            is Step3Event.SetIntroduceDetail -> {
                updateState(currentState.copy(introduceDetail = event.detail))
            }
            is Step3Event.ClearIntroduceDetail -> {
                updateState(currentState.copy(introduceDetail = ""))
            }
            is Step3Event.SetPersonalityType -> {
                updateState(currentState.copy(userPersonalityInput = event.type))
            }
            is Step3Event.ClearPersonalityType -> {
                updateState(currentState.copy(userPersonalityInput = ""))
            }
            is Step3Event.SetAdditionalDetailComment -> {
                updateState(currentState.copy(additionalComment = event.comment))
            }
        }
    }
}