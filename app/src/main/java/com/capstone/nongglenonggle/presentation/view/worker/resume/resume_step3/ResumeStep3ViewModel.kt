package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Effect as effect
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Event as event

@HiltViewModel
class ResumeStep3ViewModel @Inject constructor() :
    BaseViewModel<ResumeStep3Contract.Event, ResumeStep3Contract.State, ResumeStep3Contract.Effect>(
        initialState = ResumeStep3Contract.State()
    ) {
    override fun handleEvent(event: ResumeStep3Contract.Event) {
        super.handleEvent(event)
        when(event) {
            else -> {}
        }
    }
}