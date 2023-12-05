package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.NoticeContent
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(uid:String) : Flow<NoticeContent?> {
        return firestoreGetRepository.getNotice(uid)
    }
}