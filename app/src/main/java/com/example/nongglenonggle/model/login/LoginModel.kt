package com.example.nongglenonggle.model.login

import com.google.firebase.firestore.FirebaseFirestore

class LoginModel(private val firestore: FirebaseFirestore) {
        fun getFarmerUserCollectionPath(uid:String):String{
            return "Farmer/User/Users/$uid"
        }
    fun getWorkerUserCollectionPath(uid:String):String{
        return "Worker/User/Users/$uid"
    }
    fun getFarmerUserInfo(uid:String, onSuccess: (userInfo:UserInfo)-> Unit, onFailure:()->Unit){
        val userDocumentPath=getFarmerUserCollectionPath(uid)

        //firestore로부터 유저의 정보 가져오기
        firestore.document(userDocumentPath).get()
            .addOnSuccessListener {
                document->
                if(document.exists())
                {
                    val userInfo=document.toObject(UserInfo::class.java)
                    userInfo?.let{
                        onSuccess(userInfo)
                    } ?: onFailure()
                }else{
                    onFailure()
                }
            }.addOnFailureListener{
                onFailure()
            }
    }
    fun getWorkerUserInfo(uid:String, onSuccess: (userInfo:UserInfo)-> Unit, onFailure:()->Unit){
        val userDocumentPath=getWorkerUserCollectionPath(uid)

        //firestore로부터 유저의 정보 가져오기
        firestore.document(userDocumentPath).get()
            .addOnSuccessListener {
                    document->
                if(document.exists())
                {
                    val userInfo=document.toObject(UserInfo::class.java)
                    userInfo?.let{
                        onSuccess(userInfo)
                    } ?: onFailure()
                }else{
                    onFailure()
                }
            }.addOnFailureListener{
                onFailure()
            }
    }
}