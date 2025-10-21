package com.capstone.nongglenonggle.data.model.worker

import java.time.Period
import java.util.UUID

//경력 추가 bottomsheet

data class UserCareerListItemModel(
    val id: String = UUID.randomUUID().toString(), // 고유 ID
    val careerTitle: String = "",
    val careerPeriodText: String = "",
    val careerPeriodDetail: String = "",
    val careerContent: String = "",
    val careerPeriod: Period? = null
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "careerTitle" to careerTitle,
        "careerPeriodText" to careerPeriodText,
        "careerPeriodDetail" to careerPeriodDetail,
        "careerContent" to careerContent,
    )
}
