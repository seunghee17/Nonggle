package com.capstone.nongglenonggle.data.repositoryimpl

//
//class AuthRepositoryImpl @Inject constructor(
//    private val firebaseAuth: FirebaseAuth
//): AuthRepository {
//    override suspend fun requestPhoneNumberVerification(
//        phoneNumber: String,
//        onVerificationCompleted: (PhoneAuthCredential) -> Unit,
//        onVerificationFailed: (String) -> Unit,
//        onCodeSent: (String, PhoneAuthProvider.ForceResendingToken) -> Unit
//    ): Result<PhoneAuthProvider.OnVerificationStateChangedCallbacks> {
//        val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                onVerificationCompleted(credential)
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                val message = when(e) {
//                    is FirebaseAuthInvalidCredentialsException -> "유효하지 않은 인증번호 입니다."
//                    is FirebaseTooManyRequestsException -> ""
//                    is FirebaseAuthMissingActivityForRecaptchaException -> ""
//                    else -> ""
//                }
//                onVerificationFailed(message)
//            }
//
//            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                onCodeSent(verificationId, token)
//            }
//        }
//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(getPhoneNumberForAuth(phoneNumber))
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setCallbacks(callbacks)
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//        return Result.success(callbacks)
//    }
//
//    override suspend fun resendVerificationCode(
//        phoneNumber: String,
//        token: PhoneAuthProvider.ForceResendingToken?,
//        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
//    ) {
//        val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setCallbacks(callbacks)
//        if (token != null) {
//            optionsBuilder.setForceResendingToken(token)
//        }
//        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
//    }
//
//
//    override suspend fun signInWithPhoneAuthCredential(
//        credential: PhoneAuthCredential
//    ): Result<Unit> = suspendCancellableCoroutine { continuation ->
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    continuation.resume(Result.success(Unit))
//                } else {
//                    val errorMessage = task.exception?.message ?: "Unknown error"
//                    continuation.resume(Result.failure(Exception(errorMessage)))
//                }
//            }
//    }
//
//
//}