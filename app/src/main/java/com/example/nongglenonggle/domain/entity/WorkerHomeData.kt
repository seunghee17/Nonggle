package com.example.nongglenonggle.domain.entity

import com.google.firebase.firestore.DocumentReference

data class WorkerHomeData(
    val userName : String = "",
    val resume : DocumentReference? = null
)
