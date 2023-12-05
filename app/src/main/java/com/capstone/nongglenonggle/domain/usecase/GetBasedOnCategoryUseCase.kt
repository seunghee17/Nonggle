package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class GetBasedOnCategoryUseCase @Inject constructor(private val firestoreGetRepository: FirestoreGetRepository) {
    suspend operator fun invoke(type:String,category:String):List<DocumentReference>{
        return firestoreGetRepository.getBasedOnCategory(type,category)
    }
}