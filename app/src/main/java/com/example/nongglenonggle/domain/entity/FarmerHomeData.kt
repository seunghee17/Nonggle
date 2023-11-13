package com.example.nongglenonggle.domain.entity

import com.google.firebase.firestore.DocumentReference

data class FarmerHomeData(
    val userName : String = "",
    val refs : List<DocumentReference>? = null,
    val category1 : String = "",
    val category2 : String? = null,
    val category3 : String? = null,
    val first : String="",
    val second : String="",
    val uid:String = ""
    //추후 지원자 리스트까지 데이터 형식 추가하기
)
