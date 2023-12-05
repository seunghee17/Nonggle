package com.capstone.nongglenonggle.domain.usecase

import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import javax.inject.Inject

class FetchFirestoreDataUseCase @Inject constructor(private val repository: FirestoreGetRepository){
}