package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.entity.ResumeContent
import com.capstone.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class AddResumeUseCase @Inject constructor(private val firestoreSetRepository: FirestoreSetRepository) {
    suspend operator fun invoke(resumeContent: ResumeContent, id1:String,id2:String):DocumentReference{
        return firestoreSetRepository.addResumeData(resumeContent,id1,id2)
    }
}