package com.capstone.nongglenonggle.presentation.view.farmer.notice.compose_integration.notice_step1

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class NoticeStep1Contract {
    data class State(
        val isLoading: Boolean = false,
    ): UiState

    sealed interface Event : UiEvent {
        object goToAddressSearchScreen: Event
    }

    sealed interface Effect : UiEffect {
        data class UnAvailableToastmessage(val message: String):Effect
    }
}