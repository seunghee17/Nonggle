package com.example.nongglenonggle.domain.usecase

import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import javax.inject.Inject

class FetchFirestoreDataUseCase @Inject constructor(private val repository: FirestoreGetRepository){
    suspend fun <T> execute(clazz: Class<T>, collectionPath:String, documentPath:String? ): List<T>? {
        return repository.fetchData(clazz,collectionPath,documentPath)
    }
}