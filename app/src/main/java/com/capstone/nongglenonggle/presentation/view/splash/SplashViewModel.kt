package com.capstone.nongglenonggle.presentation.view.splash

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.onFailure
import com.capstone.nongglenonggle.data.onSuccess
import com.capstone.nongglenonggle.domain.usecase.login.GetUserAuthDataRepositoryUseCase
import com.capstone.nongglenonggle.presentation.view.signup.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserAuthDataRepositoryUseCase: GetUserAuthDataRepositoryUseCase
): BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>(initialState = SplashContract.State()) {

    init {
        getUserLoginType()
    }

    override fun handleEvent(event: SplashContract.Event) {

    }

    private fun getUserLoginType() {
        viewModelScope.launch {
            getUserAuthDataRepositoryUseCase.invoke()
                .onSuccess {
                    val userType = UserType.valueOf(it.signUpType)
                    when (userType) {
                        UserType.WORKER -> {
                            postEffect(SplashContract.Effect.NavigateToWorkerHome)
                        }
                        else -> {
                            postEffect(SplashContract.Effect.NavigateToLogin)
                        }
                    }
                }
                .onFailure {
                    postEffect(SplashContract.Effect.NavigateToLogin)
                }
        }
    }
}
