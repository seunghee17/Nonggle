package com.example.nongglenonggle.domain.repository

import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.ResumeContent
import com.google.firebase.firestore.DocumentReference

interface FirestoreSetRepository {
    suspend fun addNoticeData(noticeContent: NoticeContent):DocumentReference
    suspend fun addResumeData(resumeContent: ResumeContent, id1:String, id2:String)
    suspend fun addNoticeRefToUser(docRef: DocumentReference)

    //공고글, 이력서 공통
    suspend fun addRefToAddress(docRef: DocumentReference, type:String, id1:String, id2:String)

    suspend fun addNoticeToCategory(docRef: DocumentReference,id:String)

    suspend fun addNoticeToGender(docRef: DocumentReference,id:String)
    suspend fun addNoticeToType(docRef: DocumentReference,id:String)
}