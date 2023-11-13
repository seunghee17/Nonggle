package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoticeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(): Flow<List<SeekerHomeFilterContent>>{
        return firestoreGetRepository.getAllNotice()
    }
}