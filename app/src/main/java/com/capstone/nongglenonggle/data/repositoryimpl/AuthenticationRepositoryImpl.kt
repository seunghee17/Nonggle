package com.capstone.nongglenonggle.data.repositoryimpl

import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): AuthenticationRepository {
    override suspend fun setUserData(userData: UserDataClass) {
        val user = firebaseAuth.getCurrentUser()
        if(user?.email != null) {
            firestore.collection(user.email!!)
                .add(userData)
                .addOnSuccessListener {
                    //성공적으로 저장되었을때 수행 작업
                }
                .addOnFailureListener { e ->
                    Log.e("ERROR", e.message.toString())
                }
        }
    }

}