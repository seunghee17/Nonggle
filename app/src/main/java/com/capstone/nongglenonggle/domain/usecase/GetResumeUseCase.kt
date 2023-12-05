package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.ResumeContent
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetResumeUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(setting1:String, setting2:String,uid:String) : Flow<ResumeContent?>{
        return firestoreGetRepository.getResume(setting1,setting2,uid)
    }
}