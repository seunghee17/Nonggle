package com.example.nongglenonggle.data

import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreGetRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth) :
    FirestoreGetRepository {
        override suspend fun getWorkerInfo(): WorkerHomeData?{
            val currentUserUid = firebaseAuth.currentUser?.uid
            val docSnapshot = firestore.collection("Worker").document(currentUserUid!!).get().await()
            return docSnapshot.toObject(WorkerHomeData::class.java)
        }
}