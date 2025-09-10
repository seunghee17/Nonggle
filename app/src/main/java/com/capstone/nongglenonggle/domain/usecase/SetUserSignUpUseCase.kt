package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.data.network.AppResult
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetUserSignUpUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(userData: UserDataClass): AppResult<Unit> =
        withContext(ioDispatcher) { authenticationRepository.setUserData(userData = userData) }
}