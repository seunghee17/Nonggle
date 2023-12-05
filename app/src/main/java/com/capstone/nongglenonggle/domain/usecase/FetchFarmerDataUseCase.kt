package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.FarmerHomeData
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import javax.inject.Inject

class FetchFarmerDataUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke() : FarmerHomeData?{
        return firestoreGetRepository.getFarmerHomeInfo()
    }
}