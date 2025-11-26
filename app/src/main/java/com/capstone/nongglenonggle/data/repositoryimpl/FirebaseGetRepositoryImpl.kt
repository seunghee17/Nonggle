package com.capstone.nongglenonggle.data.repositoryimpl

import android.util.Log
import com.capstone.nongglenonggle.domain.entity.FarmerHomeData
import com.capstone.nongglenonggle.domain.entity.NoticeContent
import com.capstone.nongglenonggle.domain.entity.ResumeContent
import com.capstone.nongglenonggle.domain.entity.WorkerHomeData
import com.capstone.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
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
    override suspend fun getNotice(uid:String): Flow<NoticeContent?> {
        return flow{
            val docSnapshot = firestore.collection("Announcement").document(uid).get().await()
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

    //자신의 이력서 불러오기
    override suspend fun getResume(setting1:String, setting2:String,uid:String):Flow<ResumeContent?>{
        return flow{
            val docSnapshot = firestore.collection("Resume").document(setting1).collection(setting2).document(uid).get().await()
            emit(docSnapshot.toObject(ResumeContent::class.java))
        }.catch {
            e->
            Log.e("getResume","${e.message}")
            emit(null)
        }
    }


}