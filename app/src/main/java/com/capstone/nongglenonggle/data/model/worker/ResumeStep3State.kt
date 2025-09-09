package com.capstone.nongglenonggle.data.model.worker

data class ResumeStep3State(
    val introduceDetail: String = "",
    val userPersonalityInput: String = "",
    val userPersonalities: List<String> = emptyList(),
    val additionalComment: String = "",
)
