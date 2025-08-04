package com.capstone.nongglenonggle.presentation.view.signup

import com.capstone.nongglenonggle.core.base.UiEffect
import com.capstone.nongglenonggle.core.base.UiEvent
import com.capstone.nongglenonggle.core.base.UiState
import javax.inject.Inject

class SignupContract @Inject constructor() {
    data class State(
        val isLoading: Boolean = false,
        val SignUpStep: SignupStep = SignupStep.SET_USER_TYPE,
        val userSignupType: UserType = UserType.NONE,
        val userName: String = "",
        val phoneNumber: String = "",
        val verificationCode: String = "",
        val authverificationState: Boolean = true,
        val allCheckBoxState: Boolean = false,
        val ageLimitConfirmCheckBox: Boolean = false,
        val serviceUseTermCheckBox: Boolean = false,
        val personalInfoCheckBox: Boolean = false,
        val farmerCategory: Array<String> = arrayOf("식량작물", "채소", "과수", "특용작물", "화훼", "축산", "농기계작업", "기타"),
        val selectedFarmerCategory: List<String> = listOf(""),
    ): UiState

    sealed class Event: UiEvent {
        data class SelectUseTypeBox(val type: UserType): Event()
        //사용자 이름 작성과 삭제에 대한 event
        data class UserInsertName(val userName: String): Event()
        object ClearUserName: Event()
        //사용자 핸드폰 번호 작성과 삭제에 대한 event
        data class UserInsertPhoneNumber(val phoneNumber: String): Event()
        object ClearUserPhoneNumber: Event()
        //인증번호 전송 event
        data class sendVerificationCode(val phoneNumber: String): Event()
        //전송된 인증번호 작성과 삭제에 대한 event
        data class UserInsertVerificationCode(val code: String): Event()
        object ClearUserVerificationCode: Event()
        //전송한 인증 코드 검증 event
        data class VerificationCodeCheck(val code: String): Event()
        //약관동의 체크박스에 대한 event
        object AcitivateAllTermCheckBox: Event()
        object AcitivateAgeLimitCheckBox: Event()
        object AcitivateServiceUseTermCheckBox: Event()
        object AcitivatePersonalInfoCheckBox: Event()
    }

    sealed class Effect: UiEffect {
        object NavigateToStep1Screen: Effect()
        object NavigateToStep2Screen: Effect()
        object NavigateToStep3Screen: Effect()
        data class setToastMessage(val message: String): Effect()
    }
}