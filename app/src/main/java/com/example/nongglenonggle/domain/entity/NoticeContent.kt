package com.example.nongglenonggle.domain.entity

data class NoticeContent(
    var imageUrl : String="",
    var pay : List<String> = listOf(),
    var recruitPeriod : Map<String,Any> = emptyMap(),
    var recruitNum : String = "",
    val recruitAge : List<String> = listOf(),
    val recruitGender:String = "",
    val career : String= "",
    val qualification : Map<String,Any> = emptyMap(),
    val workPeriod:List<String> = listOf(),
    val workType:String = "",
    val workDay : Map<String,Any> = emptyMap(),
    val workTime:Map<String,Any> = emptyMap(),
    val title:String = "",
    val categoryItem:List<String> = listOf(),
    val firstAddress:String = "",
    val finalAddress:String = "",
    val workDetailInfo:String = "",
    val stayInfo:String = "",
    val mealInfo:String = "",
    val specialInfo:Map<String,Any> = emptyMap(),
    val preferential:String = "",
    val uid : String=""
)
