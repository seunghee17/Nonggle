package com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_main

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class NoticeMainContract {
    data class State(
        val isLoading: Boolean = false
    ): UiState

    sealed interface Event: UiEvent {

    }

    sealed interface Effect: UiEffect {

    }
}