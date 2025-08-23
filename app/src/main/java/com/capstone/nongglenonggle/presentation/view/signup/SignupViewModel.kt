package com.capstone.nongglenonggle.presentation.view.signup

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.domain.usecase.SetUserSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val setUserSignUpUseCase: SetUserSignUpUseCase
) : BaseViewModel<SignupContract.Event, SignupContract.State, SignupContract.Effect>(initialState = SignupContract.State()) {

    //비즈니스 로직에 필요한 변수
    //체크박스 활성화 개수
    private var activeCheckBoxCount: Int =0

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

                is SignupContract.Event.updateDoroAddress -> {
                    getAddress(event.data)
                }

                is SignupContract.Event.ClearFarmerAddressDetail -> {
                    clearAddressDetail()
                }

                is SignupContract.Event.InputFarmerAddressDetail -> {
                    updateState(currentState.copy(farmerAddressDeatail = event.detailAddress))
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
                is SignupContract.Event.navigateToStep1Button -> {
                    postEffect(effect = SignupContract.Effect.NavigateToStep1Screen)
                }
                is SignupContract.Event.navigateToStep3Button -> {
                    postEffect(effect = SignupContract.Effect.NavigateToStep3Screen)
                }
                is SignupContract.Event.navigateToHomeButton -> {
                    //사용자 정보 제출할 로직 작성
                    postEffect(effect = SignupContract.Effect.NavigateToHomeScreen)
                }
            }
        }
    }

    fun getAddress(data: String) {
        updateState(currentState.copy(farmerAddressSearchFromDoro = data))
        postEffect(effect = SignupContract.Effect.NavigateToBackScreen)
    }

    fun clearAddressDetail() {
        updateState(currentState.copy(farmerAddressDeatail = ""))
    }

    fun setLoading(loading: Boolean) {
        updateState(currentState.copy(isLoading = loading))
    }

    fun navigateToAddressScreen() {
        setLoading(true)
        postEffect(SignupContract.Effect.NavigateToAddressSearchScreen)
    }

    fun sendUserInfoToDB() {
        viewModelScope.launch {
            setUserSignUpUseCase.invoke(userData = UserDataClass(
                signUpType = currentState.userSignupType.toString(),
                farmerCategory = currentState.selectedFarmerCategory,
                farmerAddress = currentState.farmerAddressSearchFromDoro
            ))
        }
    }
}

enum class SignupStep(val stepNum: Int) {
    SET_USER_TYPE(0),
    //STEP1(1),
    STEP2(2),
    STEP3(3) //구직자만 해당하는 step
}

enum class UserType {
    WORKER, MANAGER, NONE
}