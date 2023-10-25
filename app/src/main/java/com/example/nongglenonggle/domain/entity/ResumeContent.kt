package com.example.nongglenonggle.domain.entity

data class ResumeContent(
    val imageurl: String,
    val userName:String,
    //00세로 바꿔서 넣기
    val userYear:String,
    //피그마보고 만들어야함
    val userGender:String,
    val userPresent: String,
    val allCareer:String,
)
