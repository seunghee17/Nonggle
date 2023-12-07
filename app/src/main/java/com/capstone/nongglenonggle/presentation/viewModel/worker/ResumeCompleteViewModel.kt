package com.capstone.nongglenonggle.presentation.viewModel.worker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.domain.entity.ResumeContent
import com.capstone.nongglenonggle.domain.usecase.GetResumeUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResumeCompleteViewModel @Inject constructor(
    private val getResumeUseCase: GetResumeUseCase
): ViewModel(){
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val _resumeDetail = MutableLiveData<ResumeContent>()
    val resumeDetail:LiveData<ResumeContent> = _resumeDetail

    val _resumeforFarmer = MutableLiveData<Boolean>()
    val resumeforFarmer:LiveData<Boolean> = _resumeforFarmer


    var certification:String=""
    var locations:String=""
    var items:String=""

    fun fetchResumeDetail(setting1:String, setting2:String,uid:String) {
        viewModelScope.launch {
            try {
                getResumeUseCase.invoke(setting1,setting2,uid).collect { data ->
                    if (data != null) {
                        _resumeDetail.value = data
                    } else {
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    suspend fun getOffererName():String{
        val uid = firebaseAuth.currentUser?.uid
        return withContext(Dispatchers.IO){
            try{
                val snapshot = firestore.collection("Farmer").document(uid!!).get().await()
                snapshot.getString("userName").orEmpty()
            }catch (e:Exception){
                ""
            }
        }
    }

    init{
        _resumeforFarmer.postValue(true)
    }

}