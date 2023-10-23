package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import javax.inject.Inject

class FetchFirestoreDataUseCase @Inject constructor(private val repository: FirestoreGetRepository){
}