package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SetUserSignUpUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(userData: UserDataClass) {
        authenticationRepository.setUserData(userData = userData)
    }
}