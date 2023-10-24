package com.example.nongglenonggle.domain.entity

data class NoticeContent(
    var imageUrl : String,
    var pay : List<String>,
    var recruitPeriod : Map<String,Any>,
    var recruitNum : String,
    val recruitAge : List<String>,
    val recruitGender:String,
    val career : String,
    val qualification : Map<String,Any>,
    val workPeriod:List<String>,
    val workType:String,
    val workDay : Map<String,Any>,
    val workTime:Map<String,Any>,
    val title:String,
    val categoryItem:List<String>,
    val firstAddress:String,
    val finalAddress:String,
    val workDetailInfo:String,
    val stayInfo:String,
    val mealInfo:String,
    val specialInfo:Map<String,Any>
)
