package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllResumeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke() : Flow<List<OffererHomeFilterContent>> {
        return firestoreGetRepository.getAllResume()
    }
}