package com.capstone.nongglenonggle.presentation.view.signup

import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.core.base.BaseViewModel
import com.capstone.nongglenonggle.domain.usecase.RequestPhoneNumberVerificationUseCase
import com.capstone.nongglenonggle.domain.usecase.ResendVerificationCodeUseCase
import com.capstone.nongglenonggle.domain.usecase.SignInWithPhoneAuthCredentialUseCase
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupComposeViewModel @Inject constructor(
    private val requestPhoneNumberVerificationUseCase: RequestPhoneNumberVerificationUseCase,
    private val signInWithPhoneAuthCredentialUseCase: SignInWithPhoneAuthCredentialUseCase,
    private val resendVerificationCodeUseCase: ResendVerificationCodeUseCase
) : BaseViewModel<SignupContract.Event, SignupContract.State, SignupContract.Effect>(initialState = SignupContract.State()) {

    var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    //비즈니스 로직에 필요한 변수
    //토큰 전송 횟수
    private var tokenSendCount: Int = 0
    //체크박스 활성화 개수
    private var activeCheckBoxCount: Int =0
    lateinit var userCredential: PhoneAuthCredential

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

                //사용자 핸드폰 번호 작성
                is SignupContract.Event.UserInsertPhoneNumber -> {
                    updateState(currentState.copy(phoneNumber = event.phoneNumber))
                }

                //사용자 핸드폰 번호 지우기
                is SignupContract.Event.ClearUserPhoneNumber -> {
                    updateState(currentState.copy(phoneNumber = ""))
                }

                //인증번호가 매개변수로 넘긴 핸드폰 번호로 전송된다
                is SignupContract.Event.sendVerificationCode -> {
                    if (tokenSendCount > 3) {
                        postEffect(SignupContract.Effect.setToastMessage(message = "인증 횟수를 초과했습니다 앱 종료 후 다시 시도해주세요."))
                    } else {
                        if (tokenSendCount == 0) {
                            requestPhoneNumberVerification()
                        } else {
                            resendVerificationCode(
                                phoneNumber = event.phoneNumber,
                                token = resendToken
                            )
                        }
                        tokenSendCount++
                    }
                }

                //전송된 인증번호를 작성
                is SignupContract.Event.UserInsertVerificationCode -> {
                    updateState(currentState.copy(verificationCode = event.code))
                }
                is SignupContract.Event.ClearUserVerificationCode -> {
                    updateState(currentState.copy(verificationCode = ""))
                }

                is SignupContract.Event.VerificationCodeCheck -> {
                    if(storedVerificationId.isNullOrEmpty()) {
                        sendVerificationError("인증번호가 아직 전송되지 않았습니다.")
                        return@launch
                    }
                    verifyPhoneNumberWithCode(
                        verificationId = storedVerificationId,
                        code = event.code
                    )
                    val result = signInWithPhoneAuthCredentialUseCase.invoke(credential = userCredential)
                    result.fold(
                        onSuccess = {
                            updateState(currentState.copy(authverificationState = true))
                            sendVerificationSuccess("인증에 성공하였습니다.")
                        },
                        onFailure = {
                            sendVerificationError("인증에 실패하였습니다.")
                        }
                    )
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
            }
        }
    }

    private fun sendVerificationError(errorMessage: String) {
        postEffect(SignupContract.Effect.setToastMessage(errorMessage))
    }

    private fun sendVerificationSuccess(message: String) {
        postEffect(SignupContract.Effect.setToastMessage(message))
    }

    private fun requestPhoneNumberVerification() {
        viewModelScope.launch {
            val result = requestPhoneNumberVerificationUseCase.invoke(
                phoneNumber = currentState.phoneNumber,
                onVerificationCompleted = {
                    sendVerificationSuccess("인증번호를 전송중입니다. 잠시만 기다려주세요.")
                },
                onVerificationFailed = {
                    sendVerificationError(errorMessage = "에러에러")
                },
                onCodeSent = { verificationId, token ->
                    storedVerificationId = verificationId
                    resendToken = token
                }
            )
            result.fold(
                onSuccess = {
                    callbacks = it
                },
                onFailure = {
                    sendVerificationError("예상치못한 에러가 발생했습니다.")
                }
            )
        }
    }

    //인증코드 입력된 이후 completed가 호출되면 PhoneAuthCcredential 객체 가져올 수 있음
    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        userCredential = PhoneAuthProvider.getCredential(verificationId!!, code)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        viewModelScope.launch {
            resendVerificationCodeUseCase.invoke(phoneNumber = phoneNumber, token = token, callbacks = callbacks)
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