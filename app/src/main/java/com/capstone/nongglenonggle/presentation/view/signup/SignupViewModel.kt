package com.capstone.nongglenonggle.presentation.view.signup

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.core.common.logger.AppResultMessageProvider
import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.data.AppResult
import com.capstone.nongglenonggle.data.network.onFailure
import com.capstone.nongglenonggle.data.network.onSuccess
import com.capstone.nongglenonggle.domain.usecase.GetRegionUseCase
import com.capstone.nongglenonggle.domain.usecase.SaveRegionToLocalDataBaseUseCase
import com.capstone.nongglenonggle.domain.usecase.SetUserSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.capstone.nongglenonggle.presentation.view.signup.SignupContract.Event as SignUpEvent
import com.capstone.nongglenonggle.presentation.view.signup.SignupContract.Effect as SignUpEffect
import com.capstone.nongglenonggle.presentation.view.signup.SignupContract.State as SignUpState

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val setUserSignUpUseCase: SetUserSignUpUseCase,
    private val getRegionUseCase: GetRegionUseCase,
    private val saveRegionToLocalDataBaseUseCase: SaveRegionToLocalDataBaseUseCase
) : BaseViewModel<SignUpEvent, SignUpState, SignUpEffect>(initialState = SignupContract.State()) {

    //비즈니스 로직에 필요한 변수
    //체크박스 활성화 개수
    private var activeCheckBoxCount: Int =0

    init {
        viewModelScope.launch {
            getRegionUseCase.invoke()
                .onSuccess {
                    saveRegionToLocalDataBaseUseCase.invoke(it.regions)
                }
                .onFailure {

                }
        }
    }

    override fun handleEvent(event: SignUpEvent) {
        viewModelScope.launch {
            when (event) {
                //SignupSetUserType에서 가입하는 유저의 타입 정하는 이벤트
                is SignUpEvent.SelectUseTypeBox -> {
                    updateState(currentState.copy(userSignupType = event.type))
                }

                //사용자 이름 작성
                is SignUpEvent.UserInsertName -> {
                    updateState(currentState.copy(userName = event.userName))
                }

                //사용자 이름 지우기
                is SignUpEvent.ClearUserName -> {
                    updateState(currentState.copy(userName = ""))
                }

                is SignUpEvent.ActivateAllTermCheckBox -> {
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

                is SignUpEvent.AcitivateAgeLimitCheckBox -> {
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

                is SignUpEvent.AcitivateServiceUseTermCheckBox -> {
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

                is SignUpEvent.AcitivatePersonalInfoCheckBox -> {
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

                is SignUpEvent.UpdateDoroAddress -> {
                    getAddress(event.data)
                }

                is SignUpEvent.ClearFarmerAddressDetail -> {
                    clearAddressDetail()
                }

                is SignUpEvent.InputFarmerAddressDetail -> {
                    updateState(currentState.copy(farmerAddressDeatail = event.detailAddress))
                }

                is SignUpEvent.SelectFarmerCategory -> {
                    val tmpList = currentState.selectedFarmerCategory.toMutableList()
                    if(tmpList.contains(event.category)) {
                        tmpList.remove(event.category)
                    } else if(tmpList.size < 3) {
                        tmpList.add(event.category)
                    }
                    updateState(currentState.copy(selectedFarmerCategory = tmpList))
                }
                is SignUpEvent.NavigateToStep1Button -> {
                    postEffect(effect = SignUpEffect.NavigateToStep1Screen)
                }
                is SignUpEvent.NavigateToStep3Button -> {
                    postEffect(effect = SignUpEffect.NavigateToStep3Screen)
                }
                is SignUpEvent.SaveUserInfo -> {
                    sendUserInfoToDB(context = event.context)
                }

                is SignUpEvent.NavigateToBackScreen -> {
                    postEffect(effect = SignUpEffect.NavigateToBackScreen)
                }

                is SignUpEvent.NavigateToAddressSearchScreen -> {
                    setLoading(true)
                    postEffect(SignUpEffect.NavigateToAddressSearchScreen)
                }
            }
        }
    }

    private fun getAddress(data: String) {
        updateState(currentState.copy(farmerAddressSearchFromDoro = data))
        postEffect(effect = SignUpEffect.NavigateToBackScreen)
    }

    private fun clearAddressDetail() {
        updateState(currentState.copy(farmerAddressDeatail = ""))
    }

    private fun setLoading(loading: Boolean) {
        updateState(currentState.copy(isLoading = loading))
    }

    private fun sendUserInfoToDB(context: Context) {
        if(currentState.submitState is SignupContract.SubmitState.Loading) return

        updateState(currentState.copy(isLoading = true))
        val userData = UserDataClass(
            signUpType = currentState.userSignupType.name,
            farmerCategory = currentState.selectedFarmerCategory,
            farmerAddress = currentState.farmerAddressSearchFromDoro
        )
        viewModelScope.launch {
            updateState(currentState.copy(submitState = SignupContract.SubmitState.Loading))
            val result = setUserSignUpUseCase.invoke(userData = userData)
            when(result) {
                is AppResult.Success -> {
                    updateState(currentState.copy(submitState = SignupContract.SubmitState.Success))
                    postEffect(effect = SignUpEffect.NavigateToHomeScreen)
                }
                is AppResult.Failure -> {
                    val errorMsg = AppResultMessageProvider.message(context,result)
                    postEffect(SignUpEffect.SetToastMessage(errorMsg))
                }
            }
        }
    }
}

enum class UserType {
    WORKER, MANAGER, NONE
}