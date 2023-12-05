package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.NoticeContent
import com.capstone.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class AddNoticeUseCase @Inject constructor(private val firestoreSetRepository: FirestoreSetRepository) {
    suspend operator fun invoke(noticeContent: NoticeContent):DocumentReference{
        return firestoreSetRepository.addNoticeData(noticeContent)
    }
}