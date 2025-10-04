package com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_main

import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_main.NoticeMainContract.Effect as MainEffect
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_main.NoticeMainContract.State as MainState
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_main.NoticeMainContract.Event as MainEvent

@HiltViewModel
class NoticeMainViewModel @Inject constructor() : BaseViewModel<MainEvent, MainState, MainEffect>(
    initialState = NoticeMainContract.State()
) {
    override fun handleEvent(event: NoticeMainContract.Event) {
        super.handleEvent(event)
    }
}