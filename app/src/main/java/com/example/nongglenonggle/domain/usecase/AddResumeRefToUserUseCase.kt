package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class AddResumeRefToUserUseCase @Inject constructor(private val firestoreSetRepository: FirestoreSetRepository) {
    suspend operator fun invoke(docRef: DocumentReference){
        return firestoreSetRepository.addResumeRefToUser(docRef)
    }
}