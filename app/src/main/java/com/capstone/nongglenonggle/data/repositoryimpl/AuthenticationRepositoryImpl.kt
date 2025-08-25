package com.capstone.nongglenonggle.data.repositoryimpl

import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AuthenticationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {

    //회원 정보 저장
    override suspend fun setUserData(userData: UserDataClass): Result<Unit> =
        withContext(ioDispatcher) {
            val user = firebaseAuth.currentUser
                ?: return@withContext Result.failure(IllegalStateException("Not authenticated"))

            val doc = firestore.collection("personal").document(user.uid)

            try {
                withTimeout(10_000) {
                    doc.set(userData.toMap(), SetOptions.merge()).await()
                }
                Result.success(Unit)
            } catch (e: CancellationException) {
                // 코루틴 취소는 그대로 전파 (중요)
                throw e
            } catch (e: Exception) {
                Result.failure(mapFirebaseException(e))
            }
        }

//    override suspend fun getUserData(uid: String): Result<Boolean> {
//
//    }


    private fun mapFirebaseException(e: Throwable): Throwable = when (e) {
        is FirebaseFirestoreException -> when (e.code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                SecurityException("권한이 없습니다.", e)

            else -> e
        }

        else -> e
    }

}