package com.capstone.nongglenonggle.presentation.view.splash

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.AppResult
import com.capstone.nongglenonggle.domain.usecase.GetUserAuthDataRepositoryUseCase
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
            val result = getUserAuthDataRepositoryUseCase.invoke()
            when (result) {
                is AppResult.Success -> {
                    if (UserType.valueOf(result.data.signUpType) == UserType.WORKER) {
                        postEffect(SplashContract.Effect.NavigateToWorkerHome)
                    } else {
                        postEffect(SplashContract.Effect.NavigateToLogin)
                    }
                }
                is AppResult.Failure -> {
                    postEffect(SplashContract.Effect.NavigateToLogin)
                }
            }
        }
    }
}
