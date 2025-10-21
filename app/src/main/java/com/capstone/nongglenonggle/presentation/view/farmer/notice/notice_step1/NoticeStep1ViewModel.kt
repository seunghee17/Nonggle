package com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_step1

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_step1.NoticeStep1Contract.Event as Step1Event
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_step1.NoticeStep1Contract.Effect as Step1Effect
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_step1.NoticeStep1Contract.State as Step1State

@HiltViewModel
class NoticeStep1ViewModel @Inject constructor() :
    BaseViewModel<Step1Event, Step1State, Step1Effect>(
        initialState = NoticeStep1Contract.State()
    ) {
    override fun handleEvent(event: NoticeStep1Contract.Event) {
        super.handleEvent(event)
        when (event) {
            is Step1Event.SetUserName -> {
                updateState(currentState.copy(userName = event.name))
            }

            is Step1Event.ClearUserName -> {
                updateState(currentState.copy(userName = ""))
            }

            else -> {}
        }
    }
}