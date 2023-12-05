package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoticeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(): Flow<List<SeekerHomeFilterContent>>{
        return firestoreGetRepository.getAllNotice()
    }
}