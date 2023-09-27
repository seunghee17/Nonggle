package com.example.nongglenonggle.usecase

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface VerificationEvent {
    fun onVerificationCompleted(credential: PhoneAuthCredential)
    fun onVerificationFailed(e: FirebaseException)
    fun onCodeSent(verificationId:String, token: PhoneAuthProvider.ForceResendingToken)
}