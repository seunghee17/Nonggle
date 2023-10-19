package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.Model

interface FirestoreGetRepository {
    suspend fun <T> fetchData(clazz: Class<T>, collectionPath:String, documentPath:String? = null):List<T>?
}