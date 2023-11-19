package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllResumeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke() : Flow<List<OffererHomeFilterContent>> {
        return firestoreGetRepository.getAllResume()
    }
}