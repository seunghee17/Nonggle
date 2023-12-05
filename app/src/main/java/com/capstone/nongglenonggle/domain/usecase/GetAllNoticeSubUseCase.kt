package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoticeSubUseCase @Inject constructor(private val firebaseGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(): Flow<List<WorkerSearchRecommend>>{
        return firebaseGetRepository.getAllNoticeSub()
    }
}