package com.capstone.nongglenonggle.domain.usecase
import com.capstone.nongglenonggle.domain.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthCredential
import javax.inject.Inject

class SignInWithPhoneAuthCredentialUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        credential: PhoneAuthCredential
    ): Result<Unit> {
        return authRepository.signInWithPhoneAuthCredential(credential = credential)
    }
}