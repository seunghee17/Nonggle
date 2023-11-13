package com.example.nongglenonggle.domain.entity

data class SeekerHomeFilterContent(
    val title : String = "",
    val firstAddress : String = "",
    val recruitPeriod : Map<String, Any> = emptyMap(),
    val workType:String = "",
    val pay : List<Any> = listOf(),
    val uid : String=""
)
