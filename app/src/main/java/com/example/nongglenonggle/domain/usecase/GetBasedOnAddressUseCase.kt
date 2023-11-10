package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class GetBasedOnAddressUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(type:String,first:String,second:String):List<DocumentReference>{
        return firestoreGetRepository.getBasedOnAddress(type,first,second)
    }
}