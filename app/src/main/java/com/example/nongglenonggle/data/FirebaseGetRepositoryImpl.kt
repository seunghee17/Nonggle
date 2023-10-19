package com.example.nongglenonggle.data

import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreGetRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreGetRepository {

    override suspend fun <T> fetchData(clazz: Class<T>, collectionPath: String, documentPath: String?): List<T>? {
        return withContext(Dispatchers.IO) {
            if (documentPath != null) {
                val documentReference = firestore.collection(collectionPath).document(documentPath)
                val snapshot = documentReference.get().await()
                val item = snapshot.toObject(clazz)
                item?.let { return@let listOf(it) } ?: return@withContext null
            } else {
                val collectionReference = firestore.collection(collectionPath)
                val snapshots = collectionReference.get().await()
                snapshots.toObjects(clazz)
            }
        }
    }
}