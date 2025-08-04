package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class RequestPhoneNumberVerificationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        onVerificationCompleted: (PhoneAuthCredential) -> Unit,
        onVerificationFailed: (String) -> Unit,
        onCodeSent: (String, PhoneAuthProvider.ForceResendingToken) -> Unit
    ): Result<PhoneAuthProvider.OnVerificationStateChangedCallbacks> {
        return authRepository.requestPhoneNumberVerification(
            phoneNumber,
            onVerificationCompleted,
            onVerificationFailed,
            onCodeSent
        )
    }
}
