package com.example.nongglenonggle.domain.entity

import android.net.Uri

class Model {
    data class ImageEntity(val uri: Uri)

    data class NoticeContent(
        val imageUrl : String,
        val pay : List<String>,
        val recruitPeriod : Map<String,Any>,
        val recruitNum : String,
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
        val specialInfo:String
    )
    data class ResumeSummary(
        val title : String,
        val date : String,
        val total : String,
        val description : String
    )
}