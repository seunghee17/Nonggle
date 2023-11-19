package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.FarmerHomeData
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.example.nongglenonggle.domain.entity.ResumeContent
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.example.nongglenonggle.domain.entity.WorkerFilterListData
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface FirestoreGetRepository {
    suspend fun getWorkerInfo() : WorkerHomeData?
    suspend fun getNotice(uid:String): Flow<NoticeContent?>
    suspend fun getFarmerHomeInfo() : FarmerHomeData?
    suspend fun getResume(setting1:String, setting2:String,uid:String) : Flow<ResumeContent?>
    suspend fun getBasedOnCategory(type:String,category:String):List<DocumentReference>

    suspend fun getBasedOnAddress(type:String,first:String,second:String):List<DocumentReference>
    suspend fun getAllNotice():Flow<List<SeekerHomeFilterContent>>

    suspend fun getAllNoticeSub():Flow<List<WorkerSearchRecommend>>
    suspend fun getWorkTypeNotice(type:String):Flow<List<WorkerSearchRecommend>>
    suspend fun getAllResume():Flow<List<OffererHomeFilterContent>>
}