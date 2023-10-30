package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.ResumeContent
import com.example.nongglenonggle.domain.repository.FirestoreSetRepository
import javax.inject.Inject

class AddResumeUseCase @Inject constructor(private val firestoreSetRepository: FirestoreSetRepository) {
    suspend operator fun invoke(resumeContent: ResumeContent, id1:String, id2:String){
        return firestoreSetRepository.addResumeData(resumeContent,id1, id2)
    }
}