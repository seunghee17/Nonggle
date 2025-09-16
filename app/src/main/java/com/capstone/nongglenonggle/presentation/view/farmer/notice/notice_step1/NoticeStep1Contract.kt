package com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_step1

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState

class NoticeStep1Contract {
    data class State(
        val isLoading: Boolean = false,
        val userName: String = "",
        val userPhoneNumber: String = ""
    ): UiState

    sealed interface Event : UiEvent {
        data class SetUserName(val name: String) : Event
        object ClearUserName: Event
        data class SetUserPhoneNumber(val phoneNumber: String): Event
        object ClearUserPhoneNunber: Event
        object goToAddressSearchScreen: Event
    }

    sealed interface Effect : UiEffect {
        data class UnAvailableToastmessage(val message: String): Effect
    }
}