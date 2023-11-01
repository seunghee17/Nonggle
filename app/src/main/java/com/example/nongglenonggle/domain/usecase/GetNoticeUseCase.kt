package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke() : Flow<NoticeContent?> {
        return firestoreGetRepository.getNotice()
    }
}