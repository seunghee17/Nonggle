package com.capstone.nongglenonggle.domain.repository

import com.capstone.nongglenonggle.domain.entity.FarmerHomeData
import com.capstone.nongglenonggle.domain.entity.NoticeContent
import com.capstone.nongglenonggle.domain.entity.ResumeContent
import com.capstone.nongglenonggle.domain.entity.WorkerHomeData
import kotlinx.coroutines.flow.Flow

interface FirestoreGetRepository {
    suspend fun getWorkerInfo() : WorkerHomeData?
    suspend fun getNotice(uid:String): Flow<NoticeContent?>
    suspend fun getFarmerHomeInfo() : FarmerHomeData?
    suspend fun getResume(setting1:String, setting2:String,uid:String) : Flow<ResumeContent?>
}