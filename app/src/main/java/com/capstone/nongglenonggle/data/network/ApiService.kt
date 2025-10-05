package com.capstone.nongglenonggle.data.network

import com.capstone.nongglenonggle.data.model.remote_model.RegionModel
import retrofit2.http.GET

interface ApiService {
    @GET("/all/region")
    suspend fun getRegions(): ApiResult<RegionModel>

}