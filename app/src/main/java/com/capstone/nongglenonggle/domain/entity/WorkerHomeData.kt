package com.capstone.nongglenonggle.domain.entity

import com.google.firebase.firestore.DocumentReference

data class WorkerHomeData(
    val userName : String = "",
    val refs : List<DocumentReference>? = listOf(),
)
