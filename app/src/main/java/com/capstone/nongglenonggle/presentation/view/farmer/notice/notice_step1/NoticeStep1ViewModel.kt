package com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_step1

import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Contract.Event
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
        when(event) {
            is Step1Event.SetUserName -> {
                updateState(currentState.copy(userName = event.name))
            }

            is Step1Event.ClearUserName -> {
                updateState(currentState.copy(userName = ""))
            }
            else ->{}
        }
    }

    /// FIXME: 번호가 아닌 다른 형식이 textfield에 들어올때 에러 발생시키기
    fun regPhoneNumberType() {
//        if(currentState.userName.length == 3 || currentState.userName.length == 8) {
//            val tempNumber = currentState.userPhoneNumber
//            tempNumber += "-"
//        }
    }
}