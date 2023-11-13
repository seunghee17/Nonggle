package com.example.nongglenonggle.data

import android.util.Log
import com.example.nongglenonggle.domain.entity.FarmerHomeData
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.ResumeContent
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.repository.FirestoreGetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
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
import java.lang.ref.Reference
import javax.inject.Inject
import com.example.nongglenonggle.presentation.util.getDataFromReference
import com.google.firebase.firestore.ktx.toObjects

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
            //val currentUserUid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User not log")
            //val docSnapshot = firestore.collection("Announcement").document(currentUserUid!!).get().await()
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
    override suspend fun getResume(setting1:String, setting2:String):Flow<ResumeContent?>{
        return flow{
            val currentUserUid = firebaseAuth.currentUser?.uid
            if(currentUserUid==null || setting1.isBlank() || setting2.isBlank()){
                throw IllegalStateException("Invalid settings or user not logged in")
                Log.e("getResume", "invlid user")
            }
            val docSnapshot = firestore.collection("Resume").document(setting1).collection(setting2).document(currentUserUid!!).get().await()
            emit(docSnapshot.toObject(ResumeContent::class.java))
        }.catch {
            e->
            Log.e("getResume","${e.message}")
            emit(null)
        }
    }

    //농가 품목 카테고리 기반 데이터 가져오기
    private var refs : ArrayList<DocumentReference>? = null
    override suspend fun getBasedOnCategory(type:String,category:String):List<DocumentReference>{
       try{
           val document = firestore.collection(type).document(category).get().await()
           return if(document.exists()){
               refs = document.get("refs") as? ArrayList<DocumentReference>
               return refs.orEmpty()
           }else{
               Log.e("FirebaseGetRepositoryImpl", "$type")
               return emptyList()
           }
       }catch (e:Exception){
           Log.e("FirebaseGetRepositoryImpl", "Error fetching data for type: $type, category: $category")
           return emptyList()
       }
    }
    //지역기반 데이터 가져오기
    override suspend fun getBasedOnAddress(type:String,first:String,second:String):List<DocumentReference>{
        val document = firestore.collection("LocationFilter").document(type).collection(first).document(second).get().await()
        val refs = document.get("refs") as? List<DocumentReference>
        return refs.orEmpty()
    }
    //모든 공고글 불러오기
    override suspend fun getAllNotice():Flow<List<SeekerHomeFilterContent>>{
        return flow{
            val docSnapshot = firestore.collection("Announcement").get().await()
            val documents = docSnapshot.toObjects(SeekerHomeFilterContent::class.java)
            emit(documents)
        }
    }

}