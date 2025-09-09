package com.capstone.nongglenonggle.data.model.worker

data class ResumeStep4State(
    val preferWorkCategoryList: MutableList<String> = mutableListOf(),
    val totalPreferWorkCategoryList: Array<String> = arrayOf("식량작물", "채소", "과수", "")
)
