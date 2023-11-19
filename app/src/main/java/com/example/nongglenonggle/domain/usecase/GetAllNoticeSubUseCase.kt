package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.WorkerFilterListData
import com.example.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoticeSubUseCase @Inject constructor(private val firebaseGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(): Flow<List<WorkerSearchRecommend>>{
        return firebaseGetRepository.getAllNoticeSub()
    }
}