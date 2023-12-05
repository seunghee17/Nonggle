package com.example.nongglenonggle.domain.repository

import kotlinx.coroutines.flow.Flow

interface ApplyRepository {
    suspend fun modifyFarmerDB(uid:String): Flow<Result<Unit>>
}