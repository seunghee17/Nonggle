package com.capstone.nongglenonggle.presentation.view.signup

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import javax.inject.Inject

class SignupContract @Inject constructor() {
    data class State(
        val isLoading: Boolean = true,
        val SignUpStep: SignupStep = SignupStep.SET_USER_TYPE,
        val userSignupType: UserType = UserType.NONE,
        val userName: String = "",
        val userId: String = "",
        val allCheckBoxState: Boolean = false,
        val ageLimitConfirmCheckBox: Boolean = false,
        val serviceUseTermCheckBox: Boolean = false,
        val personalInfoCheckBox: Boolean = false,
        val farmerCategory: Array<String> = arrayOf("식량작물", "채소", "과수", "특용작물", "화훼", "축산", "농기계작업", "기타"),
        val selectedFarmerCategory: List<String> = emptyList(),
        val farmerAddressSearchFromDoro: String = "",
        val farmerAddressDeatail: String = "",
    ): UiState

    sealed class Event: UiEvent {
        data class SelectUseTypeBox(val type: UserType): Event()
        //사용자 이름 작성과 삭제에 대한 event
        data class UserInsertName(val userName: String): Event()
        object ClearUserName: Event()

        //약관동의 체크박스에 대한 event
        object AcitivateAllTermCheckBox: Event()
        object AcitivateAgeLimitCheckBox: Event()
        object AcitivateServiceUseTermCheckBox: Event()
        object AcitivatePersonalInfoCheckBox: Event()
        data class updateDoroAddress(val data: String): Event()
        data class InputFarmerAddressDetail(val detailAddress: String): Event()
        object ClearFarmerAddressDetail: Event()
        data class SelectFarmerCategory(val category: String): Event()
    }

    sealed class Effect: UiEffect {
        object NavigateToStep1Screen: Effect()
        //object NavigateToStep2Screen: Effect()
        object NavigateToStep3Screen: Effect()
        object NavigateToHomeScreen: Effect()
        object NavigateToBackScreen: Effect()
        object NavigateToAddressSearchScreen: Effect()
        data class setToastMessage(val message: String): Effect()
    }
}