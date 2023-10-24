package com.example.nongglenonggle.data

import android.util.Log
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.repository.FirestoreSetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class FirestoreSetRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth:FirebaseAuth
): FirestoreSetRepository {
    //왜 withcontext?
    //왜 트라이캐치?
    //Result<Void?>무슨뜻?
    //await()?
    override suspend fun addNoticeData(noticeContent:NoticeContent)= withContext(Dispatchers.IO){
        try{
            val currentUserUid = firebaseAuth.currentUser?.uid
            if(currentUserUid == null){
                Log.e("error","user is not valid")
            }
            else{
                firestore.collection("Announcement").document(currentUserUid).set(noticeContent).await()
            }
        }catch (e:Exception){
            Log.e("error","user is not valid")
        }
        return@withContext Unit
    }


}