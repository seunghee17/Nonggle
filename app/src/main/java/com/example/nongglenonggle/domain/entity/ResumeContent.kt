package com.example.nongglenonggle.domain.entity

data class ResumeContent(
    val imageurl: String,
    val userName:String,
    //00세로 바꿔서 넣기
    val userYear:Int,
    val userGender:String,
    //짧은 자기소개
    val userPresent: String,
    //전체경력
    val allCareer:String,
    val resumeData:List<Model.ResumeSummary>,
    //자격증입력
    val careerList: List<String>,
    //희망근무장소
    val locationSelect:List<String>,
    //희망 근무 형태
    val dormType:String,
    //주 0일
    val dayData:String,
    val dayDetailData:String,
    //희망품목
    val desiredItem : List<String>,
    val selfHastag:List<String>,
    val selfInfo:String
)
