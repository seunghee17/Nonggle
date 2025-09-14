package com.capstone.nongglenonggle.data.model.worker

import java.util.Date
import java.util.UUID

//경력 추가 bottomsheet
//FIXME: 마이그레이션 해야할 부분

data class ResumeStep2UserCareerListItem(
    val id: String = UUID.randomUUID().toString(), // 고유 ID
    val careerTitle: String = "",
    val careerPeriod: String = "",
    val careerPeriodDetail: String = "",
    val careerContent: String = ""
)
