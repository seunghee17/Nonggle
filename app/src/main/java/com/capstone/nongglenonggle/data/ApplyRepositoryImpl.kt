package com.capstone.nongglenonggle.data

import android.util.Log
import com.capstone.nongglenonggle.domain.repository.ApplyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ApplyRepositoryImpl @Inject constructor(
    private val firestore:FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
):ApplyRepository {
    override suspend fun modifyFarmerDB(uid:String): Flow<Result<Unit>> = flow{
        //구인자 db에서 suggest1값 저장
        val docRef = firestore.collection("Farmer").document(uid)
        try{
            val doc = docRef.get().await()
            val fieldValue = doc.getString("suggest1")
            Log.e("ApplyRepositoryImpl", "${fieldValue}")
            if(doc.get("applier") == null){
                //배열 없다면 생성
                docRef.update(mapOf("applier" to listOf(fieldValue)))
                Log.e("ApplyRepositoryImpl", "new array")
            }
            else{
                //배열 있음
                docRef.update("applier",FieldValue.arrayUnion(fieldValue))
                Log.e("ApplyRepositoryImpl", "exists array")
            }
            val updates = hashMapOf<String,Any>("suggest1" to FieldValue.delete())
            docRef.update(updates).await()
            emit(Result.success(Unit))
            Log.e("ApplyRepositoryImpl", "done delete")
        } catch (e:Exception){
            emit(Result.failure(e))
            Log.e("ApplyRepositoryImpl","$e")
        }

    }
}