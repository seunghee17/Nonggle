package com.capstone.nongglenonggle.domain.repository

import com.capstone.nongglenonggle.data.model.remote_model.SubRegion

interface LocalDataRepository {
    suspend fun saveRegionToLocalDB(subRegions: List<SubRegion>)
}