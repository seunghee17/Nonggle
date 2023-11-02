package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.FarmerHomeData
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import javax.inject.Inject

class FetchFarmerDataUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke() : FarmerHomeData?{
        return firestoreGetRepository.getFarmerHomeInfo()
    }
}