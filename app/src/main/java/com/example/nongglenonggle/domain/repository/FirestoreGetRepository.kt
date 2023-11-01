package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import kotlinx.coroutines.flow.Flow

interface FirestoreGetRepository {
    suspend fun getWorkerInfo() : WorkerHomeData?
    suspend fun getNotice(): Flow<NoticeContent?>
}