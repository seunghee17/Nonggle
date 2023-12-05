package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class AddResumeRefToUserUseCase @Inject constructor(private val firestoreSetRepository: FirestoreSetRepository) {
    suspend operator fun invoke(docRef: DocumentReference){
        return firestoreSetRepository.addResumeRefToUser(docRef)
    }
}