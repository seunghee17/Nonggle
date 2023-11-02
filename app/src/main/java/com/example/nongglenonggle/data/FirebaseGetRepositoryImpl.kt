package com.example.nongglenonggle.data

import android.util.Log
import com.example.nongglenonggle.domain.entity.FarmerHomeData
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
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

    //자신의 공고글 불러오기
    override suspend fun getNotice(): Flow<NoticeContent?> {
        return flow{
            val currentUserUid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not log")
            val docSnapshot = firestore.collection("Announcement").document(currentUserUid!!).get().await()
            emit(docSnapshot.toObject(NoticeContent::class.java))
        }.catch {
            e->
            Log.e("firebasegetimpl","${e.message}")
            emit(null)
        }
    }

    override suspend fun getFarmerHomeInfo():FarmerHomeData?{
        val currentUserUid = firebaseAuth.currentUser?.uid
        val docSnapshot = firestore.collection("Farmer").document(currentUserUid!!).get().await()
        return docSnapshot.toObject(FarmerHomeData::class.java)
    }

}