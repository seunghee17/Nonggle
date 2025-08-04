package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class ResendVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        return authRepository.resendVerificationCode(phoneNumber, token, callbacks)
    }
}