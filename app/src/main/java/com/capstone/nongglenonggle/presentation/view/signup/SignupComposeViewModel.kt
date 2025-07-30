package com.capstone.nongglenonggle.presentation.view.signup

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupComposeViewModel @Inject constructor(

) : BaseViewModel<SignupContract.Event, SignupContract.State, SignupContract.Effect>(initialState = SignupContract.State()) {

    override fun reduceState(event: SignupContract.Event) {
        viewModelScope.launch {
            when(event) {
                is SignupContract.Event.SelectUseTypeBox -> {
                    setUserSignupType(event.type)
                }
            }
        }
    }

    private fun setUserSignupType(type: UserType) {
        updateState(currentState.copy(userSignupType = type))
    }

}

enum class UserType {
    WORKER, MANAGER, NONE
}