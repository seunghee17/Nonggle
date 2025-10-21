package com.capstone.nongglenonggle.domain.repository

import com.capstone.nongglenonggle.data.model.remote_model.RegionModel
import com.capstone.nongglenonggle.data.network.ApiResult

interface RemoteDataRepository {
    suspend fun getRegion() : ApiResult<RegionModel>
}