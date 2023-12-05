package com.capstone.nongglenonggle.presentation.viewModel.worker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nongglenonggle.domain.entity.ResumeContent
import com.capstone.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.capstone.nongglenonggle.domain.entity.WorkerHomeData
import com.capstone.nongglenonggle.domain.usecase.FetchWorkerDataUseCase
import com.capstone.nongglenonggle.domain.usecase.GetAllNoticeUseCase
import com.capstone.nongglenonggle.domain.usecase.ModifyFarmerDBUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class WorkerHomeViewModel @Inject constructor(
    private val fetchWorkerDataUseCase: FetchWorkerDataUseCase,
    private val getAllNoticeUseCase: GetAllNoticeUseCase,
    private val modifyFarmerDBUseCase: ModifyFarmerDBUseCase
) :ViewModel(){
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _userDetail = MutableLiveData<WorkerHomeData?>()
    val userDetail : LiveData<WorkerHomeData?> = _userDetail

    private val _allNotice = MutableLiveData<List<SeekerHomeFilterContent>>()
    val allNotice:LiveData<List<SeekerHomeFilterContent>> = _allNotice

    val _homeResume = MutableLiveData<ResumeContent>()
    val homeResume:LiveData<ResumeContent> = _homeResume

    val _suggestComplete = MutableLiveData<Boolean>()
    val suggestComplete:LiveData<Boolean> = _suggestComplete

    suspend fun alarmSuggestionOk(){
        viewModelScope.launch {
            val uid = getFarmerUID(firebaseAuth.currentUser!!.uid)
            modifyFarmerDBUseCase.invoke(uid).collect{result->
                Log.d("alarmSuggestionOk","${uid}")
                result.onSuccess {
                    _suggestComplete.value = true
                    Log.d("alarmSuggestionOk","${_suggestComplete.value}")
                }
                result.onFailure {
                    _suggestComplete.value = false
                    Log.d("alarmSuggestionOk","${_suggestComplete.value}")
                }
            }
        }
    }

    fun alarmSuggestionCancel(): Flow<String> = callbackFlow{
        val docRef = firestore.collection("Worker").document(firebaseAuth.currentUser!!.uid)
        val updates = hashMapOf<String,Any>(
            "offererName" to FieldValue.delete(),
            "suggest1" to FieldValue.delete()
        )
    docRef.update(updates).addOnCompleteListener{
        trySend("completed").isSuccess
    }
        awaitClose {  }
    }


    fun fetchUserInfo(){
        viewModelScope.launch {
            val user = fetchWorkerDataUseCase.invoke()
            _userDetail.value = user
        }
    }
    fun getAllNotice(){
        viewModelScope.launch {
            getAllNoticeUseCase().collect{data->
                _allNotice.value = data
            }
        }
    }

    val _isResume = MutableLiveData<Boolean>()
    val isResume:LiveData<Boolean> = _isResume

    private val _haveData = MutableLiveData<Boolean>()
    val haveData:LiveData<Boolean> = _haveData

    fun updateVisible(){
        if(allNotice.value != null){
            _haveData.value = true
        }
        else{
            _haveData.value = false
        }
    }

    fun fetchResumeVisible(){
        if(_userDetail.value?.refs != null){
            _isResume.value = true
            Log.d("fetchResumeVisible", "true")
        }
        else{
            _isResume.value = false
            Log.d("fetchResumeVisible", "false")
        }
    }
    suspend fun setUserFromRef(documentReference: DocumentReference) : ResumeContent?{
        return try{
            val documentSnapshot = documentReference.get().await()
            documentSnapshot.toObject(ResumeContent::class.java)
        }catch (e:Exception){
            null
        }
    }

    suspend fun getFarmerUID(uid:String):String{
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Worker").document(uid)
        val doc = docRef.get().await()
        return try {
            if(doc.exists()){
                doc.getString("suggest1") ?: ""
            }else{
                ""
            }
        }catch (e:Exception){
            Log.e("error","$e")
            ""
        }
    }
    init{
        fetchUserInfo()
        fetchResumeVisible()
        getAllNotice()
        _haveData.value = false
    }
}