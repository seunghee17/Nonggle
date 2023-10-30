package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import javax.inject.Inject

class FetchWorkerDataUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke():WorkerHomeData?{
        return firestoreGetRepository.getWorkerInfo()
    }
}