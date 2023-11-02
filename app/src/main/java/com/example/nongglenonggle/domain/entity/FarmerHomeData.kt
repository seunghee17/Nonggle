package com.example.nongglenonggle.domain.entity

import com.google.firebase.firestore.DocumentReference

data class FarmerHomeData(
    val userName : String = "",
    val notice : DocumentReference? = null
    //추후 지원자 리스트까지 데이터 형식 추가하기
)
