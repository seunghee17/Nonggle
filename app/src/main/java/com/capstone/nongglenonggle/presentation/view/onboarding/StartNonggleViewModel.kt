package com.capstone.nongglenonggle.presentation.view.onboarding

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartNonggleViewModel @Inject constructor(

) : BaseViewModel<StartNonggleContract.Event, StartNonggleContract.State, StartNonggleContract.Effect>(initialState = StartNonggleContract.State()) {

    override fun reduceState(event: StartNonggleContract.Event) {
        viewModelScope.launch {
            when(event) {
                is StartNonggleContract.Event.kakaoLoginButtonClickEvent -> setUnavailableToastMessage()
                is StartNonggleContract.Event.googleLoginButtonClickEvent -> setUnavailableToastMessage()
                is StartNonggleContract.Event.signUpWithPhoneNumberButtonClickEvent -> navigateToSignUp()
                is StartNonggleContract.Event.loginButtonClickEvent -> navigateToLogin()
            }
        }
    }

    fun setUnavailableToastMessage() {
        postEffect(StartNonggleContract.Effect.unAvailableToastmessage("검수 중입니다"))
    }

    private fun navigateToSignUp() {
        postEffect(StartNonggleContract.Effect.NavigateToSignUp)
    }

    private fun navigateToLogin() {
        postEffect(StartNonggleContract.Effect.NavigateToLogin)
    }

}

