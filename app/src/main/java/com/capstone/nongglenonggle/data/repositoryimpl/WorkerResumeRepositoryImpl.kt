package com.capstone.nongglenonggle.data.repositoryimpl

import android.net.Uri
import com.capstone.nongglenonggle.core.common.logger.AppResultLogger
import com.capstone.nongglenonggle.core.common.logger.logFailure
import com.capstone.nongglenonggle.data.AppResult
import com.capstone.nongglenonggle.data.model.worker.UserResumeModel
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.capstone.nongglenonggle.domain.repository.WorkerResumeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAVAILABLE
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class WorkerResumeRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
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
                AppResultLogger.logFailure<WorkerResumeRepositoryImpl>(failure)
                failure
            } catch (e: Exception) {
                val failure = AppResult.Failure.Unknown(e)
                AppResultLogger.logFailure<WorkerResumeRepositoryImpl>(failure)
                failure
            }
        }
    }

    override suspend fun setWorkerResume(resume: UserResumeModel): AppResult<Unit> {
        val user = firebaseAuth.currentUser
            ?: return AppResult.Failure.Unknown(Throwable("회원정보 저장에 실패했습니다. 다시 시도해주세요."))
        return withContext(ioDispatcher) {
            try {
                withTimeout(10_000) {
                    val resumeData = mapOf(
                        "uid" to user.uid,
                        "name" to resume.name,
                        "birthDate" to resume.birthDate,
                        "gender" to resume.gender,
                        "certificate" to resume.certificate,
                        "certificateList" to resume.certificateList,
                        "careerList" to resume.careerList.map { it.toMap() },
                        "regionList" to resume.regionList,
                        "categoryList" to resume.categoryList,
                    )
                    firestore.collection("personal")
                        .document(user.uid) // 사용자별 resume 문서
                        .collection("resume")
                        .document("info")   // resume 하위 문서
                        .set(resumeData, SetOptions.merge()) // 기존 필드 유지
                        .await()
                }
                AppResult.success(Unit)
            } catch (e: FirebaseFirestoreException) {
                val failure = when (e.code) {
                    PERMISSION_DENIED -> AppResult.Failure.PermissionDenied(e)
                    UNAVAILABLE -> AppResult.Failure.NetworkError(e)
                    else -> AppResult.Failure.Unknown(e)
                }
                AppResultLogger.logFailure(failure)
                failure
            } catch (e: CancellationException) {
                val failure = AppResult.Failure.Cancelled(e)
                AppResultLogger.logFailure(failure)
                failure
            } catch (e: Exception) {
                val failure = AppResult.Failure.Unknown(e)
                AppResultLogger.logFailure(failure)
                failure
            }
        }
    }


}