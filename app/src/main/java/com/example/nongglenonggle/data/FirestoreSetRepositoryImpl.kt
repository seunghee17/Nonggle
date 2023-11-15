package com.example.nongglenonggle.data

import android.util.Log
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.ResumeContent
import com.example.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

class FirestoreSetRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth:FirebaseAuth
): FirestoreSetRepository {
    override suspend fun addNoticeData(noticeContent: NoticeContent): DocumentReference = withContext(Dispatchers.IO) {
        val currentUserUid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User is not valid")

        val docRef = firestore.collection("Announcement").document(currentUserUid)

        try {
            docRef.set(noticeContent).await()
            return@withContext docRef
        } catch (e: Exception) {
            Log.e("error", "Failed to add notice data: ${e.message}")
            throw e
        }
    }



    override suspend fun addResumeData(resumeContent: ResumeContent, id1 :String, id2:String): DocumentReference = withContext(Dispatchers.IO) {
        val currentUserUid = firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User is not valid")

        val docRef = firestore.collection("Resume").document(id1).collection(id2).document(currentUserUid)

        try {
            docRef.set(resumeContent).await()
            return@withContext docRef
        } catch (e: Exception) {
            Log.e("FirestoreSetRepositoryImpl", "Failed to add notice data: ${e.message}")
            throw e
        }
    }

    //공고글 ref 구인자 개인table에 저장경로
    override suspend fun addNoticeRefToUser(docRef: DocumentReference) = withContext(Dispatchers.IO){
        try{
            val currentUid = firebaseAuth.currentUser?.uid
            if(currentUid == null){
                Log.e("error","user is not valid")
            }
            else{
                val userdoc = firestore.collection("Farmer").document(currentUid)
                val update = hashMapOf("refs" to FieldValue.arrayUnion(docRef))
                userdoc.set(update, SetOptions.merge()).await()
            }
        }catch (e:Exception){
            Log.e("error","user is not valid")
        }
        return@withContext Unit
    }

    //주소 필터링에 이력서 및 공고글 넣어놓기 위해서
    override suspend fun addRefToAddress(docRef: DocumentReference, type:String, id1:String, id2:String) = withContext(Dispatchers.IO){
        try{
            val storeDoc = firestore.collection("LocationFilter").document(type).collection(id1).document(id2)
            val update = hashMapOf("refs" to FieldValue.arrayUnion(docRef))
            storeDoc.set(update, SetOptions.merge()).await()}
        catch (e:Exception){
            Log.e("error","user is not valid")
        }
        return@withContext Unit
    }
//공고글 카테고리에 저장하기 위함
    override suspend fun addNoticeToCategory(name:String,docRef: DocumentReference,id:String)= withContext(Dispatchers.IO){
        try{
            val storeDoc = firestore.collection(name).document(id)
            val update = hashMapOf("refs" to FieldValue.arrayUnion(docRef))
            storeDoc.set(update, SetOptions.merge()).await()}catch (e:Exception){
            Log.e("error","user is not valid")
        }
    return@withContext Unit
    }

    //공고글 성별에 따른 저장
    override suspend fun addNoticeToGender(name:String,docRef: DocumentReference,id:String)= withContext(Dispatchers.IO){
        try{
            val storeDoc = firestore.collection(name).document(id)
            val update = hashMapOf("refs" to FieldValue.arrayUnion(docRef))
            storeDoc.set(update, SetOptions.merge()).await()
        }catch (e:Exception){
            Log.e("error","user is not valid")
            throw e
        }
        return@withContext Unit
    }

    //근무 형태별 저장
    override suspend fun addType(name:String,docRef: DocumentReference,id:String)= withContext(Dispatchers.IO){
        try{
            val storeDoc = firestore.collection(name).document(id)
            val update = hashMapOf("refs" to FieldValue.arrayUnion(docRef))
            storeDoc.set(update, SetOptions.merge()).await()
        }catch (e:Exception){
            Log.e("error","user is not valid")
        }
        return@withContext Unit
    }


    //구직자 개인 테이블 저장용
    override suspend fun addResumeRefToUser(docRef: DocumentReference) = withContext(Dispatchers.IO){
        try{
            val currentUid = firebaseAuth.currentUser?.uid
            if(currentUid == null){
                Log.e("addResumeRefToUser","user is not valid")
            }
            else{
                val userdoc = firestore.collection("Worker").document(currentUid)
                val update = hashMapOf("refs" to FieldValue.arrayUnion(docRef))
                userdoc.set(update, SetOptions.merge()).await()
            }
        }catch (e:Exception){
            Log.e("addResumeRefToUser","user is not valid")
        }
        return@withContext Unit
    }


    override suspend fun addByAge(docRef:DocumentReference, id:String) = withContext(Dispatchers.IO){
        try{
            val storeDoc = firestore.collection("FilterByAge").document(id)
            val update = hashMapOf("refs" to FieldValue.arrayUnion(docRef))
            storeDoc.set(update, SetOptions.merge()).await()
        }catch (e:Exception){
            Log.e("addByAge","$e")
            throw e
        }
        return@withContext Unit
    }

}