package com.capstone.nongglenonggle.data.model

/**
 * UI 선택 리스트용 flat한 DTO
 * (서울 - 강남구, 강원 - 춘천시 같은 형태로 사용 가능)
 */
data class RegionSelectModel(
    val leafRegion: String
)