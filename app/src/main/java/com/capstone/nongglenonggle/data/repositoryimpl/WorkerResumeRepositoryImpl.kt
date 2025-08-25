package com.capstone.nongglenonggle.data.repositoryimpl

import android.net.Uri
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.WorkerResumeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class WorkerResumeRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WorkerResumeRepository {

    override suspend fun setWorkerProfileImage(
        imageUri: Uri,
    ): Result<String> {
        return withContext(ioDispatcher) {
            val user = firebaseAuth.currentUser
            val uid = user?.uid ?: "unKnownUser"

            val imageFileName = "profile_" + uid + "_png"
            val storageRef = firebaseStorage.reference.child(uid).child(imageFileName)

            try {
                storageRef.putFile(imageUri).await()
                val imageurl = storageRef.downloadUrl.await()
                //업로드 성공시 이미지 url 반환하도록 구현
                Result.success(imageurl.toString())
            } catch (e: CancellationException) {
                // 코루틴 취소는 그대로 전파
                throw e
            } catch (e: Exception) {
                Result.failure(mapFirebaseStorageException(e))
            }
        }
    }

    private fun mapFirebaseStorageException(e: Throwable): Throwable = when (e) {
        is FirebaseFirestoreException -> when (e.code) {
            FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                SecurityException("권한이 없습니다.", e)

            else -> SecurityException("기타 에러", e)
        }
//            FirebaseFirestoreException.Code.CANCELLED ->
//            FirebaseFirestoreException.Code.UNKNOWN ->
//            FirebaseFirestoreException.Code.INVALID_ARGUMENT ->
//            FirebaseFirestoreException.Code.DEADLINE_EXCEEDED ->
//            FirebaseFirestoreException.Code.NOT_FOUND ->
//            FirebaseFirestoreException.Code.ALREADY_EXISTS ->
//            FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED ->
//            FirebaseFirestoreException.Code.FAILED_PRECONDITION ->
//            FirebaseFirestoreException.Code.ABORTED ->
//            FirebaseFirestoreException.Code.OUT_OF_RANGE ->
//            FirebaseFirestoreException.Code.UNIMPLEMENTED ->
//            FirebaseFirestoreException.Code.INTERNAL ->
//            FirebaseFirestoreException.Code.UNAVAILABLE ->
//            FirebaseFirestoreException.Code.DATA_LOSS ->
//            FirebaseFirestoreException.Code.UNAUTHENTICATED ->
        else -> e
    }
}