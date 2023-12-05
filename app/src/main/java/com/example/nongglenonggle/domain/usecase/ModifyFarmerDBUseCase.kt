package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.repository.ApplyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ModifyFarmerDBUseCase @Inject constructor(
    private val applyRepository: ApplyRepository) {
    suspend operator fun invoke(uid:String): Flow<Result<Unit>> {
        return applyRepository.modifyFarmerDB(uid)
    }
}