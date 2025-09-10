package com.capstone.nongglenonggle.data.repositoryimpl

import android.net.Uri
import com.capstone.nongglenonggle.core.common.logger.AppResultLogger
import com.capstone.nongglenonggle.core.common.logger.logFailure
import com.capstone.nongglenonggle.data.network.AppResult
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.WorkerResumeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAVAILABLE
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
    ): AppResult<String> {
        return withContext(ioDispatcher) {
            val user = firebaseAuth.currentUser
            val uid = user?.uid ?: "unKnownUser"

            val imageFileName = "profile_" + uid + "_png"
            val storageRef = firebaseStorage.reference.child(uid).child(imageFileName)

            try {
                storageRef.putFile(imageUri).await()
                val imageurl = storageRef.downloadUrl.await()
                //업로드 성공시 이미지 url 반환하도록 구현
                AppResult.success(imageurl.toString())
            } catch (e: FirebaseFirestoreException) {
                val failure = when (e.code) {
                    PERMISSION_DENIED -> AppResult.Failure.PermissionDenied(e)
                    UNAVAILABLE -> AppResult.Failure.NetworkError(e)
                    else -> AppResult.Failure.Unknown(e)
                }
                AppResultLogger.logFailure<AuthenticationRepositoryImpl>(failure)
                failure
            } catch (e: Exception) {
                val failure = AppResult.Failure.Unknown(e)
                AppResultLogger.logFailure<AuthenticationRepositoryImpl>(failure)
                failure
            }
        }
    }
}