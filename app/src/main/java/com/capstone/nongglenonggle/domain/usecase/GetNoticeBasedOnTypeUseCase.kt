package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeBasedOnTypeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(type:String): Flow<List<WorkerSearchRecommend>> {
        return firestoreGetRepository.getWorkTypeNotice(type)
    }
}