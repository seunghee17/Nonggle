package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class AddGenderUseCase @Inject constructor(private val firestoreSetRepository: FirestoreSetRepository) {
    suspend operator fun invoke(name:String,docRef:DocumentReference, id:String){
        return firestoreSetRepository.addNoticeToGender(name,docRef, id)
    }
}