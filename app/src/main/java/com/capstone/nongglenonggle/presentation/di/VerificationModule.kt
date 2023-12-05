package com.capstone.nongglenonggle.presentation.di

import com.capstone.nongglenonggle.domain.usecase.VerificationEvent
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object VerificationModule {
    @Provides
    fun provideVerificationCallbacks(event: VerificationEvent) : PhoneAuthProvider.OnVerificationStateChangedCallbacks{
        return object: PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //이런식으로 작성해도 되는건가..?
                event.onVerificationCompleted(credential)

            }

            override fun onVerificationFailed(e: FirebaseException) {
                event.onVerificationFailed(e)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                event.onCodeSent(verificationId, token)
            }

        }
    }

}