package com.capstone.nongglenonggle.domain.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks

interface AuthRepository {
    suspend fun requestPhoneNumberVerification(
        phoneNumber: String,
        onVerificationCompleted: (PhoneAuthCredential) -> Unit,
        onVerificationFailed: (String) -> Unit,
        onCodeSent: (String, PhoneAuthProvider.ForceResendingToken) -> Unit
    ): Result<PhoneAuthProvider.OnVerificationStateChangedCallbacks>

    suspend fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?,
        callbacks: OnVerificationStateChangedCallbacks
    )

    suspend fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
    ): Result<Unit>
}