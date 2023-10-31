package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class AddRefToAddressUseCase @Inject constructor(private val firestoreSetRepository: FirestoreSetRepository) {
    suspend operator fun invoke(docRef: DocumentReference, type:String, id1:String, id2:String){
        return firestoreSetRepository.addRefToAddress(docRef, type,id1,id2)
    }
}