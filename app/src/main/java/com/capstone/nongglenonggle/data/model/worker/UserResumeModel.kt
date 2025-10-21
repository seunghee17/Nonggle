package com.capstone.nongglenonggle.data.model.worker

data class UserResumeModel (
    val name: String,
    val birthDate: String,
    val gender: String,
    val certificate: Boolean,
    val certificateList: List<String>,
    val careerList: List<UserCareerListItemModel>,
    val regionList: List<String>,
    val categoryList: List<String>,
)