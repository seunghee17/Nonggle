package com.capstone.nongglenonggle.data.repositoryimpl


import com.capstone.nongglenonggle.core.common.logger.AppResultLogger
import com.capstone.nongglenonggle.core.common.logger.logFailure
import com.capstone.nongglenonggle.data.model.sign_up.UserDataClass
import com.capstone.nongglenonggle.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.capstone.nongglenonggle.data.network.AppResult
import com.capstone.nongglenonggle.domain.qualifiers.IoDispatcher
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED
import com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAVAILABLE
import kotlin.coroutines.cancellation.CancellationException

class AuthenticationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {

    //회원 정보 저장
    override suspend fun setUserData(userData: UserDataClass): AppResult<Unit> {
        val user = firebaseAuth.currentUser
            ?: return AppResult.Failure.Unknown(Throwable("회원정보 저장에 실패했습니다. 다시 시도해주세요."))

        return withContext(ioDispatcher) {
            try {
                withTimeout(10_000) {
                    firestore.collection("personal")
                        .document(user.uid)
                        .set(userData.toMap(), SetOptions.merge())
                        .await()
                }
                AppResult.success(Unit)
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

    override suspend fun getUserData(): AppResult<UserDataClass> =
        withContext(ioDispatcher) {
            val user = firebaseAuth.currentUser
            if (user == null) {
                val failure = AppResult.Failure.Unknown(Throwable("회원정보 저장에 실패했습니다. 다시 시도해주세요."))
                AppResultLogger.logFailure<AuthenticationRepositoryImpl>(failure)
                failure

            } else {
                val doc = firestore.collection("personal").document(user.uid)

                try {
                    val snapshot = withTimeout(10_000) { doc.get().await() }

                    val model = snapshot.toObject(UserDataClass::class.java) ?: throw Exception()
                    AppResult.success(model)
                } catch (e: CancellationException) {
                    throw e
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