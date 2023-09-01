package com.example.nongglenonggle.viewModel.farmer.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine


class SignupAuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore

    //회원가입 상태 전달용
    private val _isSignupAvailable = MutableLiveData<Boolean>()
    val isSignupAvailable: LiveData<Boolean>
        get() = _isSignupAvailable

    suspend fun signUpWithEmailPasswordAndPhoneNumber(email: String,password:String,name:String)
    {
        return suspendCancellableCoroutine {
            continuation->
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if(task.isSuccessful)
                    {
                        val user:FirebaseUser? = auth.currentUser
                        user?.let{
                            val userDocument = firestore.collection("Farmer").document("User").collection("Users").document(it.uid)
                            userDocument.set(
                                mapOf(
                                    "uid" to it.uid,
                                    "phoneNum" to email,
                                    "userName" to name
                                )
                            )
                        }
                        //코루틴 사용할 수 있지 않을까?
                        _isSignupAvailable.value = true
                    }
                    else{
                        _isSignupAvailable.value=false
                    }
                }
        }
    }
}