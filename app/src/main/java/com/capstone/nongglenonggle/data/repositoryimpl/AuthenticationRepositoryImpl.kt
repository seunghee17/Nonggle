package com.capstone.nongglenonggle.data.repositoryimpl

import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

//사용자 정보 저장 및 로그인
class AuthenticationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {

}