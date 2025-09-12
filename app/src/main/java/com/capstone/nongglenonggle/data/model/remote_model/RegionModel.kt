package com.capstone.nongglenonggle.data.model.remote_model

data class RegionModel(
    val regions: List<SubRegion>,
)

data class SubRegion(
    val name: String,
    val districts: List<String>,
)