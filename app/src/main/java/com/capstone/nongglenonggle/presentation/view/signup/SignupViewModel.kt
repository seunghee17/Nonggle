package com.capstone.nongglenonggle.presentation.view.signup

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupComposeViewModel @Inject constructor() : BaseViewModel<SignupContract.Event, SignupContract.State, SignupContract.Effect>(initialState = SignupContract.State()) {

    //비즈니스 로직에 필요한 변수
    //체크박스 활성화 개수
    private var activeCheckBoxCount: Int =0

    init {
        updateState(
            currentState.copy(
                userId = "",
                userName = ""
            )
        )
    }

    //side Effect를 최소화하고 상태 업데이트 순수기능에만 집중해야함
    override fun reduceState(event: SignupContract.Event) {
        viewModelScope.launch {
            when (event) {
                //SignupSetUserType에서 가입하는 유저의 타입 정하는 이벤트
                is SignupContract.Event.SelectUseTypeBox -> {
                    updateState(currentState.copy(userSignupType = event.type))
                }

                //사용자 이름 작성
                is SignupContract.Event.UserInsertName -> {
                    updateState(currentState.copy(userName = event.userName))
                }

                //사용자 이름 지우기
                is SignupContract.Event.ClearUserName -> {
                    updateState(currentState.copy(userName = ""))
                }

                is SignupContract.Event.AcitivateAllTermCheckBox -> {
                    val currentAllCheckboxState = currentState.allCheckBoxState
                    updateState(currentState.copy(allCheckBoxState = !currentAllCheckboxState))
                    updateState(
                        currentState.copy(
                            ageLimitConfirmCheckBox = !currentAllCheckboxState,
                            serviceUseTermCheckBox = !currentAllCheckboxState,
                            personalInfoCheckBox = !currentAllCheckboxState
                        )
                    )
                    if(currentState.allCheckBoxState) {
                        activeCheckBoxCount =3
                    } else {
                        activeCheckBoxCount=0
                    }
                }

                is SignupContract.Event.AcitivateAgeLimitCheckBox -> {
                    updateState(currentState.copy(ageLimitConfirmCheckBox = !currentState.ageLimitConfirmCheckBox))
                    if(currentState.ageLimitConfirmCheckBox) {
                        activeCheckBoxCount+=1
                    } else {
                        activeCheckBoxCount-=1
                    }
                    if(activeCheckBoxCount==3) {
                        updateState(currentState.copy(allCheckBoxState = true))
                    } else {
                        updateState(currentState.copy(allCheckBoxState = false))
                    }
                }

                is SignupContract.Event.AcitivateServiceUseTermCheckBox -> {
                    updateState(currentState.copy(serviceUseTermCheckBox = !currentState.serviceUseTermCheckBox))
                    if(currentState.serviceUseTermCheckBox) {
                        activeCheckBoxCount+=1
                    } else {
                        activeCheckBoxCount-=1
                    }
                    if(activeCheckBoxCount==3) {
                        updateState(currentState.copy(allCheckBoxState = true))
                    } else {
                        updateState(currentState.copy(allCheckBoxState = false))
                    }
                }

                is SignupContract.Event.AcitivatePersonalInfoCheckBox -> {
                    updateState(currentState.copy(personalInfoCheckBox = !currentState.personalInfoCheckBox))

                    if(currentState.personalInfoCheckBox) {
                        activeCheckBoxCount+=1
                    } else {
                        activeCheckBoxCount-=1
                    }
                    if(activeCheckBoxCount==3) {
                        updateState(currentState.copy(allCheckBoxState = true))
                    } else {
                        updateState(currentState.copy(allCheckBoxState = false))
                    }
                }

                is SignupContract.Event.SelectFarmerCategory -> {
                    val tmpList = currentState.selectedFarmerCategory.toMutableList()
                    if(tmpList.contains(event.category)) {
                        tmpList.remove(event.category)
                    } else if(tmpList.size < 3) {
                        tmpList.add(event.category)
                    }
                    updateState(currentState.copy(selectedFarmerCategory = tmpList))
                }
            }
        }
    }

}

enum class SignupStep(val stepNum: Int) {
    SET_USER_TYPE(0),
    STEP1(1),
    STEP2(2),
    STEP3(3) //구직자만 해당하는 step
}

enum class UserType {
    WORKER, MANAGER, NONE
}