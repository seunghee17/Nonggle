package com.capstone.nongglenonggle.domain.repository

import com.capstone.nongglenonggle.data.model.worker.RegionListModel

interface RegionRepository {
    suspend fun saveRegionToLocalDB(subRegions: List<RegionListModel>)
    suspend fun getRegionList(): List<String>
    suspend fun getSubRegionList(parentRegion: String): List<String>
}