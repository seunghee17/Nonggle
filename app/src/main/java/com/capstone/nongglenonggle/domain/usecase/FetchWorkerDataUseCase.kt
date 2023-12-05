package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.WorkerHomeData
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import javax.inject.Inject

class FetchWorkerDataUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke():WorkerHomeData?{
        return firestoreGetRepository.getWorkerInfo()
    }
}