package com.example.nongglenonggle.presentation.viewModel.farmer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nongglenonggle.domain.entity.ScoreDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FarmerMyPageViewModel:ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val useruid= firebaseAuth.currentUser?.uid

    private val _workuid=MutableLiveData<String>()
    val workuid:LiveData<String> = _workuid

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    //val docs = firestore.collection("Farmer").document(useruid!!)

    //var suggestionData = ScoreDataModel()
    val _suggestionData = MutableLiveData<ScoreDataModel>()
    val suggestionData:LiveData<ScoreDataModel> = _suggestionData

    fun getData(){
        val docs = firestore.collection("Farmer").document(useruid!!)
        docs.get().addOnSuccessListener { document->
            if(document != null){
                _userName.value = document.data?.get("userName").toString()
            }
        }.addOnFailureListener{exception->
            Log.e("FarmerMyPageViewModel","$exception")
        }
    }

    fun getSuggestionData(){
        val docs = firestore.collection("Farmer").document(useruid!!)
        docs.get().addOnSuccessListener { document->
            if(document != null && document.data != null){
                _workuid.value = document.data?.get("suggest1").toString()
                // Firestore에서 가져온 데이터를 ScoreDataModel 객체로 변환
//                val scoreData = ScoreDataModel(
//                    userName = document.data?.get("userName").toString(),
//                    userGender = document.data?.get("userGender").toString(),
//                    userYear = document.data?.get("userAge") as? Int ?: 0
//                )
                //_suggestionData.value = scoreData // LiveData 업데이트
            } else {
                Log.e("FarmerMyPageViewModel","data is no where")
            }
        }.addOnFailureListener{e->
            Log.e("FarmerMyPageViewModel","$e")
        }
    }
//        fun getSuggestionData(uid:String) {
//            val docs = firestore.collection("Farmer").document(useruid!!)
//            docs.get().addOnSuccessListener { document ->
//                if (document != null && document.data != null) {
//                    val scoreData = ScoreDataModel(
//                        userName = document.data?.get("userName").toString(),
//                        userGender = document.data?.get("userGender").toString(),
//                        userYear = document.data?.get("userAge") as? Int ?: 0
//                    )
//                    _suggestionData.value = scoreData
//                } else {
//                    Log.e("FarmerMyPageViewModel", "data is no where")
//                }
//            }.addOnFailureListener { e ->
//                Log.e("FarmerMyPageViewModel", "Error: $e")
//            }
//        }

    init{
        getData()
    }

}