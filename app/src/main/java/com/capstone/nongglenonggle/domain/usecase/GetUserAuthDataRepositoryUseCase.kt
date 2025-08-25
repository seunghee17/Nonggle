package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserAuthDataRepositoryUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
//    suspend operator fun invoke(uid: String): Result<Boolean> {
//        return authenticationRepository.getUserData(uid)
//    }
}