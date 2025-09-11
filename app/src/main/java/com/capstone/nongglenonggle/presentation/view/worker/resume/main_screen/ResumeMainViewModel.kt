package com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResumeMainViewModel @Inject constructor() : BaseViewModel<ResumeTabContract.Event, ResumeTabContract.State, ResumeTabContract.Effect>(
    initialState = ResumeTabContract.State()
){
    override fun handleEvent(event: ResumeTabContract.Event) {
        super.handleEvent(event)
    }
}